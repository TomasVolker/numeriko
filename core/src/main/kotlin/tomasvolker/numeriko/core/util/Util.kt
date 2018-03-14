package tomasvolker.numeriko.core.util

import tomasvolker.numeriko.core.interfaces.int.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.int.arraynd.IntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.interfaces.int.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.arraynd.set

fun computeSizeFromShape(shape: IntArray): Int {

    var result = 1

    for (i in shape.indices) {

        require(shape[i] >= 0) {
            "dimension $i size must be non negative (${shape[i]})"
        }

        result *= shape[i]
    }

    return result
}

internal fun dimensionWidthArray(
        shapeArray: ReadOnlyIntArray1D,
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
    var currentSize: Int

    for (dimension in shapeArray.indices.reversed()) {

        currentSize = shapeArray[dimension]

        index = indexFunction(dimension)

        checkRange(dimension, currentSize, index)

        result += ((index + currentSize) % currentSize) * factor
        factor *= currentSize
    }

    return result

}

internal fun indexArrayToLinearIndex(shapeArray: IntArray, indexArray: IntArray): Int {

    require(indexArray.size == shapeArray.size) {
        "indexArray (${indexArray.size}) and shapeArray (${shapeArray.size}) must have the same size. If you need a view use getView()."
    }

    return indexArrayToLinearIndex(
            shapeArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun indexArrayToLinearIndex(shapeArray: IntArray, indexArray: ReadOnlyIntArrayND): Int {

    require(indexArray.rank == 1) {
        "indexArray rank (${indexArray.rank}) must be 1"
    }

    require(indexArray.size == shapeArray.size) {
        "indexArray (${indexArray.rank}) and shapeArray (${shapeArray.size}) must have the same size"
    }

    return indexArrayToLinearIndex(
            shapeArray,
            { dimension -> indexArray[dimension] }
    )
}

internal fun linearIndexToIndexArray(shapeArray: ReadOnlyIntArray1D, linearIndex: Int): IntArray {

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
/*
inline fun <T> setAll(shape: ReadOnlyIntArrayND, data: Array<Any?>, setter: (indexArray: ReadOnlyIntArrayND) -> T) {

    val indexArray = jvmArrayNDFactory.intArray(shape.shape)

    for (i in data.indices) {

        data[i] = setter(indexArray)

        incrementIndexArray(shape, indexArray)

    }

}
*/
fun incrementIndexArray(
        shape: IntArray,
        indexArray: IntArray1D,
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

    // Overflow
    if (carry != 0) {
        indexArray[0] += carry * shape[0]
    }

}

internal fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        offset: Int,
        strideArray: IntArray,
        indexArray: IntArray,
        checkRange: Boolean = true
): Int {

    require(indexArray.size == shapeArray.size) {
        "indexArray (${indexArray.size}) and shapeArray (${shapeArray.size}) must have the same size. If you need a view use getView()."
    }

    return viewIndexArrayToLinearIndex(
            shapeArray,
            offset,
            strideArray,
            { dimension -> indexArray[dimension] },
            checkRange
    )
}

internal fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        offset: Int,
        strideArray: IntArray,
        indexArray: ReadOnlyIntArray1D,
        checkRange: Boolean = true
): Int {

    require(indexArray.rank == 1) {
        "indexArray rank (${indexArray.rank}) must be 1"
    }

    require(indexArray.size == shapeArray.size) {
        "indexArray (${indexArray.rank}) and shapeArray (${shapeArray.size}) must have the same size"
    }

    return viewIndexArrayToLinearIndex(
            shapeArray,
            offset,
            strideArray,
            { dimension -> indexArray[dimension] },
            checkRange
    )
}

private inline fun viewIndexArrayToLinearIndex(
        shapeArray: IntArray,
        offset: Int,
        strideArray: IntArray,
        indexFunction: (dimension: Int) -> Int,
        checkRange: Boolean = true
): Int {

    var result = offset
    var size: Int
    var index: Int

    for (dimension in shapeArray.lastIndex downTo 0) {

        index = indexFunction(dimension)
        size = shapeArray[dimension]

        if (checkRange) checkRange(dimension, size, index)

        result += ((index + size) % size) * strideArray[dimension]
    }

    return result

}

fun checkRange(dimension: Int, size: Int, index: Int) {
    if (index !in -size until size)/*index < -shape || shape <= index*/
        throw IndexOutOfBoundsException("index ${index} in dimension $dimension exceeds size ${size}")
}
