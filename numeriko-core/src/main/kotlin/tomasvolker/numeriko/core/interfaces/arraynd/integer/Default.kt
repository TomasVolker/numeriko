package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.arrayAlongAxis
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices

fun defaultEquals(array1: IntArrayND, array2: IntArrayND): Boolean {

    if(array1.shape != array2.shape)
        return false

    array1.fastForEachIndices { indices ->
        if (array1.get(*indices) != array2.get(*indices))
            return false
    }

    return true
}

fun defaultHashCode(array1: IntArrayND): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}

fun defaultToString(array: IntArrayND): String =
        when(array.rank) {
            0 -> array.get().toString()
            1 -> array.joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]"
            )
            else -> (0 until array.shape[0])
                    .map { array.arrayAlongAxis(axis = 0, index = it) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }
