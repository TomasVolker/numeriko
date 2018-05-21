package tomasvolker.numeriko.core.interfaces.generic.util

import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D

val Array1D<*>.lastIndex: Int get() = size -1

fun defaultEquals(array1: Array1D<*>, array2: Array1D<*>): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getValue(i) != array2.getValue(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: Array1D<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun defaultToString(array1: Array1D<*>): String =
        array1.joinToString(
                separator = ", ",
                prefix = "[ ",
                postfix = " ]",
                limit = 20,
                truncated = "..."
        )
