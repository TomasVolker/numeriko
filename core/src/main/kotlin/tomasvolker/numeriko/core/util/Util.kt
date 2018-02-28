package tomasvolker.numeriko.core.util

import tomasvolker.numeriko.core.array.arrayNDArrayFactory
import tomasvolker.numeriko.core.interfaces.integer.IntNDArray
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.interfaces.integer.get
import tomasvolker.numeriko.core.interfaces.integer.set

fun computeSizeFromShape(shape: IntArray): Int {

    var result = 1

    for (i in shape.indices) {

        require(shape[i] > 0) {
            "dimension $i size must be positive (${shape[i]})"
        }

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

    for (dimension in result.indices.reversed()) {
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

    for (dimension in shapeArray.indices.reversed()) {

        shape = shapeArray[dimension]

        index = indexFunction(dimension)

        if (index < -shape || shape <= index)
            throw IndexOutOfBoundsException("Index ${index} in rank $dimension exceeds size ${shape}")

        result += ((index + shape) % shape) * factor
        factor *= shape
    }

    return result

}

internal fun indexArrayToLinearIndex(shapeArray: IntArray, indexArray: IntArray): Int {

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
        "shapeArray rank (${shapeArray.size}) must be 1"
    }

    require(indexArray.rank == shapeArray.size) {
        "indexArray (${indexArray.rank}) and shapeArray (${shapeArray.size}) must have the same size"
    }

    return indexArrayToLinearIndex(
            shapeArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun linearIndexToIndexArray(shapeArray: ReadOnlyIntNDArray, linearIndex: Int): IntArray {

    require(shapeArray.rank == 1) { "shapeArray rank must be 1" }

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

fun incrementIndexArray(
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
        offset: Int,
        strideArray: IntArray,
        indexArray: IntArray
): Int {
    return viewIndexArrayToLinearIndex(
            shapeArray,
            offset,
            strideArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        offset: Int,
        strideArray: IntArray,
        indexArray: ReadOnlyIntNDArray
): Int {
    return viewIndexArrayToLinearIndex(
            shapeArray,
            offset,
            strideArray,
            { dimension -> indexArray[dimension] }
    )
}

private inline fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        offset: Int,
        strideArray: IntArray,
        indexFunction: (dimension: Int) -> Int
): Int {

    var result = offset
    var shape: Int
    var index: Int

    for (dimension in shapeArray.lastIndex downTo 0) {

        index = indexFunction(dimension)
        shape = shapeArray[dimension]

        if (index < -shape || shape <= index)
            throw IndexOutOfBoundsException("index ${index} in dimension $dimension exceeds size ${shape}")

        result += ((index + shape) % shape) * strideArray[dimension]
    }

    return result

}
