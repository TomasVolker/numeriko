package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.performance.fastForEachIndices
import tomasvolker.numeriko.core.performance.indexIncrement

fun defaultEquals(array1: DoubleArrayND, array2: DoubleArrayND): Boolean {

    if(array1.shape != array2.shape)
        return false

    array1.fastForEachIndices { indices ->
        if (array1.get(*indices) != array2.get(*indices))
            return false
    }

    return true
}

fun defaultHashCode(array1: DoubleArrayND): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}

fun DoubleArrayND.subArray(i: Int): DoubleArrayND =
        getView(*Array(rank) { axis -> if (axis==0) (i..i).toIndexProgression() else All }).lowerRank(0)

fun defaultToString(array: DoubleArrayND): String =
        when(array.rank) {
            0 -> array.get().toString()
            1 -> array.joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]"
            )
            else -> (0 until array.shape[0])
                    .map { array.subArray(it) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }

class DefaultDoubleArrayNDIterator(
        val array: DoubleArrayND
): DoubleIterator() {

    var currentIndex = IntArray(array.rank) { 0 }
    private var shape = IntArray(array.rank) { i -> array.shape(i) }

    var overflow = false

    override fun hasNext(): Boolean = !overflow

    override fun nextDouble(): Double =
            array.get(*currentIndex).also {
                overflow = currentIndex.indexIncrement(shape)
            }

}
