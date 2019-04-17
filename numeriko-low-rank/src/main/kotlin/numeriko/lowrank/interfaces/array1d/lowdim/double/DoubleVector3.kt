package numeriko.lowrank.interfaces.array1d.lowdim.double

import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import numeriko.lowrank.interfaces.array1d.double.defaultEquals
import numeriko.lowrank.interfaces.array1d.double.defaultHashCode
import numeriko.lowrank.interfaces.array1d.generic.defaultToString
import numeriko.lowrank.interfaces.array1d.lowdim.generic.Vector3

class DoubleVector3(
        val value0: Double,
        val value1: Double,
        val value2: Double
): Vector3<Double>, DoubleArray1D {

    override operator fun get(i0: Int) = when(i0) {
        0 -> value0
        1 -> value1
        2 -> value2
        else -> throw IndexOutOfBoundsException("$i0")
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return this.defaultEquals(other)
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}
