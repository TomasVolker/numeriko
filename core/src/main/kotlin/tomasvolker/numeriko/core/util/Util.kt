package tomasvolker.numeriko.core.util

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

fun computeSizeFromShape(shape: IntArray): Int {

    var result = 1

    for (i in shape.indices) {

        if(shape[i] <= 0)
            throw IllegalArgumentException("dimension $i$ size cannot be negative ${shape[i]}")

        result *= shape[i]
    }

    return result
}

internal fun dimensionWidthArray(
        shapeArray: ReadOnlyIntNDArray,
        strideArray: IntArray = IntArray(shapeArray.size) { 1 }
): IntArray {

    val result = IntArray(shapeArray.size)

    var factor = 1

    for (dimension in result.lastIndex downTo 0) {
        result[dimension] = factor * strideArray[dimension]
        factor *= shapeArray[dimension]
    }

    return result
}

internal inline fun indexArrayToLinearIndex(
        shapeArray: IntArray,
        indexFunction: (dimension: Int) -> Int
): Int {

    var result = 0
    var factor = 1

    var index: Int
    var shape: Int

    for (dimension in shapeArray.lastIndex downTo 0) {

        shape = shapeArray[dimension]

        index = indexFunction(dimension)

        if (index < -shape || shape <= index)
            throw IndexOutOfBoundsException("Index ${index - shape} in dimension $dimension exceeds size ${shape}")

        result += ((index + shape) % shape) * factor
        factor *= shape
    }

    return result

}

internal fun indexArrayToLinearIndex(shapeArray: IntArray, indexArray: IntArray): Int {

    require(shapeArray.size == 1) {
        "shapeArray dimension (${shapeArray.size}) must be 1"
    }

    require(indexArray.size == shapeArray.size) {
        "indexArray (${indexArray.size}) and shapeArray (${shapeArray.size}) must have the same size"
    }

    return indexArrayToLinearIndex(
            shapeArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun indexArrayToLinearIndex(shapeArray: IntArray, indexArray: ReadOnlyIntNDArray): Int {

    require(shapeArray.size == 1) {
        "shapeArray dimension (${shapeArray.size}) must be 1"
    }

    require(indexArray.dimension == shapeArray.size) {
        "indexArray (${indexArray.dimension}) and shapeArray (${shapeArray.size}) must have the same size"
    }

    return indexArrayToLinearIndex(
            shapeArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun linearIndexToIndexArray(shapeArray: IntNDArray, linearIndex: Int): IntArray {

    require(shapeArray.dimension == 1) { "shapeArray dimension must be 1" }

    val result = dimensionWidthArray(shapeArray)

    var remainder = linearIndex
    var dimensionWidth: Int

    for (dimension in 0 .. result.lastIndex) {
        dimensionWidth = result[dimension]
        result[dimension] = remainder / dimensionWidth
        remainder %= dimensionWidth
    }

    return result
}

inline fun <T> setAll(shape: ReadOnlyIntNDArray, data: Array<Any?>, setter: (indexArray: ReadOnlyIntNDArray) -> T) {

    val indexArray = arrayNDArrayFactory.intArray(shape.shape)

    for (i in data.indices) {

        data[i] = setter(indexArray)

        incrementIndexArray(shape, indexArray)

    }

}

inline fun incrementIndexArray(
        shape: ReadOnlyIntNDArray,
        indexArray: IntNDArray,
        dimension: Int = indexArray.lastIndex(0),
        amount: Int = 1) {

    var dimensionIndex = dimension
    var size: Int
    var carry = amount
    var aux: Int

    while (dimensionIndex >= 0) {

        size = shape[dimensionIndex]

        aux = indexArray[dimensionIndex] + carry

        if (aux >= 0) {
            carry = aux / size
            indexArray[dimensionIndex] = aux % size
        } else {
            carry = aux / size - 1
            indexArray[dimensionIndex] = size + aux % size
        }

        if (carry == 0) {
            break
        } else {
            dimensionIndex--
        }
    }

}

internal fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        viewShapeArray: IntArray,
        offsetArray: IntArray,
        strideArray: IntArray,
        collapsedArray: BooleanArray,
        indexArray: IntArray
): Int {
    return viewIndexArrayToLinearIndex(
            shapeArray,
            viewShapeArray,
            offsetArray,
            strideArray,
            collapsedArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        viewShapeArray: IntArray,
        offsetArray: IntArray,
        strideArray: IntArray,
        collapsedArray: BooleanArray,
        indexArray: ReadOnlyIntNDArray
): Int {
    return viewIndexArrayToLinearIndex(
            shapeArray,
            viewShapeArray,
            offsetArray,
            strideArray,
            collapsedArray,
            { dimension -> indexArray[dimension] }
    )
}

private inline fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        viewShapeArray: IntArray,
        offsetArray: IntArray,
        strideArray: IntArray,
        collapsedArray: BooleanArray,
        indexFunction: (dimension: Int) -> Int
): Int {

    var result = 0
    var factor = 1

    var backingIndex: Int
    var backingShape: Int
    var viewShape: Int

    var viewDimension = 0

    var currentIndex: Int

    for (dimension in shapeArray.lastIndex downTo 0) {

        backingShape = shapeArray[dimension]
        viewShape = viewShapeArray[dimension]

        if (collapsedArray[dimension]) {
            currentIndex = 0
        } else {
            currentIndex = indexFunction(viewDimension)
            viewDimension++
        }

        backingIndex = offsetArray[dimension] + strideArray[dimension] * currentIndex

        //Check range including negative
        if (backingIndex < -viewShape || backingShape <= backingIndex)
            throw IndexOutOfBoundsException("index ${backingIndex - backingShape} in dimension $dimension exceeds size ${backingShape}")

        result += ((backingIndex + viewShape) % viewShape) * factor
        factor *= backingShape
    }

    return result

}
