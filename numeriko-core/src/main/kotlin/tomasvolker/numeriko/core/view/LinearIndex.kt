package tomasvolker.numeriko.core.view

fun linearIndex(offset: Int, strideArray: IntArray, indices: IntArray): Int {
    var result = offset
    for (axis in 0 until strideArray.size) {
        result += strideArray[axis] * indices[axis]
    }
    return result
}

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int): Int =
        offset + strideArray[0] * i0

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int, i1: Int): Int =
        offset + strideArray[0] * i0 + strideArray[1] * i1

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int, i1: Int, i2: Int): Int =
        offset + strideArray[0] * i0 + strideArray[1] * i1 + strideArray[2] * i2

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int, i1: Int, i2: Int, i3: Int): Int =
        offset + strideArray[0] * i0 + strideArray[1] * i1 + strideArray[2] * i2 + strideArray[3] * i3

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int =
        offset + strideArray[0] * i0 + strideArray[1] * i1 + strideArray[2] * i2 + strideArray[3] * i3 + strideArray[4] * i4

fun linearIndex(offset: Int, strideArray: IntArray, i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int =
        offset + strideArray[0] * i0 + strideArray[1] * i1 + strideArray[2] * i2 + strideArray[3] * i3 + strideArray[4] * i4 + strideArray[5] * i5
