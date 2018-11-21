package tomasvolker.numeriko.core.interfaces.array1d.lowdim.double

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.lowdim.generic.Vector2

class DoubleVector2(
        val value0: Double,
        val value1: Double
): Vector2<Double>, DoubleArray1D {

    override fun getDouble(i0: Int) = when(i0) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

    override operator fun component1(): Double = value0
    override operator fun component2(): Double = value1

}

class MutableDoubleVector2(
        var value0: Double,
        var value1: Double
): Vector2<Double>, MutableDoubleArray1D {

    override fun getDouble(i0: Int) = when(i0) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun setDouble(value: Double, i0: Int) = when(i0) {
        0 -> value0 = value
        1 -> value1 = value
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleVector2) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

    override operator fun component1(): Double = value0
    override operator fun component2(): Double = value1

}