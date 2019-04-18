package tomasvolker.numeriko.core.view


sealed class ElementOrder {

    abstract fun strideArray(shape: IntArray): IntArray

    abstract fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray

}

object ContiguousFirstAxis: ElementOrder() {

    override fun strideArray(shape: IntArray): IntArray =
            IntArray(shape.size).apply {
                if (shape.isNotEmpty()) {
                    this[0] = 1
                    for (axis in 1 until shape.size) {
                        this[axis] = this[axis - 1] * shape[axis - 1]
                    }
                }
            }

    override fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray =
            IntArray(strideArray.size) { a ->
                (if(a == strideArray.size-1)
                    linearIndex
                else
                    linearIndex % strideArray[a+1]
                ) / strideArray[a]
            }

}

object ContiguousLastAxis: ElementOrder() {

    override fun strideArray(shape: IntArray): IntArray =
            IntArray(shape.size).apply {
                if (shape.isNotEmpty()) {
                    val lastAxis = shape.lastIndex
                    this[lastAxis] = 1
                    for (axis in lastAxis-1 downTo 0) {
                        this[axis] = this[axis+1] * shape[axis+1]
                    }
                }
            }

    override fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray =
            IntArray(strideArray.size) { a ->
                (if(a == 0)
                    linearIndex
                else
                    linearIndex % strideArray[a-1]
                ) / strideArray[a]
            }

}

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
