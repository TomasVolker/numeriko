package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.interfaces.array1d.generic.indices

fun defaultEquals(array1: DoubleArray1D, array2: DoubleArray1D): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getDouble(i) != array2.getDouble(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: DoubleArray1D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}

class DefaultDoubleArray1DIterator(
        val array: DoubleArray1D
): DoubleIterator() {

    var index = 0

    override fun hasNext(): Boolean =
            index < array.size

    override fun nextDouble(): Double =
            array.getDouble(index).apply { index++ }

}
