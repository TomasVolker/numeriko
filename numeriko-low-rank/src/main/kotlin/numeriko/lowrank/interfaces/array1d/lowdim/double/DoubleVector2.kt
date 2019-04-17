package numeriko.lowrank.interfaces.array1d.lowdim.double

import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import numeriko.lowrank.interfaces.array1d.double.defaultEquals
import numeriko.lowrank.interfaces.array1d.double.defaultHashCode
import numeriko.lowrank.interfaces.array1d.generic.defaultToString
import numeriko.lowrank.interfaces.array1d.lowdim.generic.Vector2

class DoubleVector2(
        val value0: Double,
        val value1: Double
): Vector2<Double>, DoubleArray1D {

    override fun get(i0: Int) = when(i0) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return this.defaultEquals(other)
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

    override operator fun component1(): Double = value0
    override operator fun component2(): Double = value1

}

class MutableDoubleVector2(
        var value0: Double,
        var value1: Double
): Vector2<Double>, MutableDoubleArray1D {

    override fun get(i0: Int) = when(i0) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun set(i0: Int, value: Double) = when(i0) {
        0 -> value0 = value
        1 -> value1 = value
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleVector2) return false
        return this.defaultEquals(other)
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

    override operator fun component1(): Double = value0
    override operator fun component2(): Double = value1

}