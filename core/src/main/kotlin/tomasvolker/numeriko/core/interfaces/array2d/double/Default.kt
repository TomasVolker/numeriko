package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.preconditions.requireSameShape

fun defaultEquals(array1: DoubleArray2D, array2: DoubleArray2D): Boolean {

    requireSameShape(array1, array2)

    array1.forEachIndex { i0, i1 ->
        if (array1.getDouble(i0, i1) != array2.getDouble(i0, i1))
            return false
    }

    return true
}

fun defaultHashCode(array1: DoubleArray2D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}


class DefaultDoubleArray2DIterator(
        val array: DoubleArray2D
): DoubleIterator() {

    var i0 = 0
    var i1 = 0

    override fun hasNext(): Boolean =
            i0 < array.shape0 && i1 < array.shape1

    override fun nextDouble(): Double {
        val result = array.getDouble(i0, i1)
        i1++
        if (i1 == array.shape1) {
            i1 = 0
            i0++
        }
        return result
    }

}
