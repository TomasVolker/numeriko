package tomasvolker.numeriko.core.interfaces.integer.util

import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.IntArray1D

fun defaultEquals(array1: IntArray1D, array2: IntArray1D): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getInt(i) != array2.getInt(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: IntArray1D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}