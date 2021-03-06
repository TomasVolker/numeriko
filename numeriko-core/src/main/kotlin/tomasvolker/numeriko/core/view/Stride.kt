package tomasvolker.numeriko.core.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D


sealed class ElementOrder {

    abstract fun strideArray(shape: IntArray1D): IntArray

    abstract fun linearToIndices(linearIndex: Int, strideArray: IntArray): IntArray

}

object ContiguousFirstAxis: ElementOrder() {

    override fun strideArray(shape: IntArray1D): IntArray =
            IntArray(shape.size).apply {
                this[0] = 1
                for (axis in 1 until shape.size) {
                    this[axis] = this[axis-1] * shape[axis-1]
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

    override fun strideArray(shape: IntArray1D): IntArray =
            IntArray(shape.size).apply {
                val lastAxis = shape.lastIndex
                this[lastAxis] = 1
                for (axis in lastAxis-1 downTo 0) {
                    this[axis] = this[axis+1] * shape[axis+1]
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

fun IntArray.without(index: Int): IntArray {

    if (index !in 0 until size)
        throw IndexOutOfBoundsException("$index")

    return IntArray(size - 1) { i ->
        if (i < index)
            this[i]
        else
            this[i + 1]
    }
}

fun IntArray.with(value: Int, index: Int): IntArray {

    if (index !in 0..size)
        throw IndexOutOfBoundsException("$index")

    return IntArray(size + 1) { i ->
        when {
            i < index -> this[i]
            i == index -> value
            else -> this[i - 1]
        }
    }
}

fun linearIndex(offset: Int, strideArray: IntArray, indices: IntArray): Int {
    var result = offset
    for (axis in 0 until strideArray.size) {
        result += strideArray[axis] * indices[axis]
    }
    return result
}
