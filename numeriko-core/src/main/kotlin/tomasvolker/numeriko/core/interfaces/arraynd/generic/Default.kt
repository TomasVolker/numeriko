package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.performance.fastForEachIndices
import tomasvolker.numeriko.core.performance.indexIncrement

fun defaultEquals(array1: ArrayND<*>, array2: ArrayND<*>): Boolean {

    if(array1.shape != array2.shape)
        return false

    array1.fastForEachIndices { indices ->
        if (array1.getValue(indices) != array2.getValue(indices))
            return false
    }

    return true
}

fun defaultHashCode(array1: ArrayND<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun <T> ArrayND<T>.subArray(i: Int): ArrayND<T> =
        arrayAlongAxis(axis = 0, index = i)

fun defaultToString(array: ArrayND<*>): String =
        when(array.rank) {
            0 -> array.getValue().toString()
            1 -> array.joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]"
            )
            else -> (0 until array.shape(0))
                    .map { i -> array.arrayAlongAxis(axis = 0, index = i) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }

class DefaultArrayNDIterator<T>(
    val array: ArrayND<T>
): Iterator<T> {

    var currentIndexArray = IntArray(array.rank) { 0 }
    var shapeArray = IntArray(array.rank) { i -> array.shape(i) }

    var currentIndex: IntArray1D
        get() = currentIndexArray.asIntArray1D()
        set(value) { currentIndexArray = value.toIntArray() }

    var overflow = false

    override fun hasNext(): Boolean = !overflow

    override fun next(): T =
            array.getValue(currentIndexArray).also {
                overflow = currentIndexArray.indexIncrement(shapeArray)
            }

}

