package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.mutableIntZeros

fun defaultEquals(array1: ArrayND<*>, array2: ArrayND<*>): Boolean {

    if(array1.shape != array2.shape)
        return false

    array1.forEachIndex { indices ->
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
        getView(*Array(rank) { d -> if (d==0) LiteralIndex(i)..i else All })
                .collapseView(0)

fun defaultToString(array: ArrayND<*>): String =
        when(array.rank) {
            0 -> array.getValue().toString()
            1 -> array.joinToString(
                    separator = ", ",
                    prefix = "[",
                    postfix = "]"
            )
            else -> (0 until array.shape[0])
                    .map { array.subArray(it) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[",
                            postfix = "]"
                    )
        }

class DefaultArrayNDIterator<T>(
    val array: ArrayND<T>
): Iterator<T> {

    var currentIndex = mutableIntZeros(array.rank)

    var overflow = false

    override fun hasNext(): Boolean = !overflow

    override fun next(): T =
            array.getValue(currentIndex).also {
                overflow = currentIndex.indexIncrement(array.shape)
            }

}

