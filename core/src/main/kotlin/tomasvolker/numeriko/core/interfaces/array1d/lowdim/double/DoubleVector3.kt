package tomasvolker.numeriko.core.interfaces.array1d.lowdim.double

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.Generic.Vector3

class DoubleVector3(
        val value0: Double,
        val value1: Double,
        val value2: Double
): Vector3<Double>, DoubleArray1D {

    override fun getDouble(index: Int) = when(index) {
        0 -> value0
        1 -> value1
        2 -> value2
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}
