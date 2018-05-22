package tomasvolker.numeriko.core.implementations.jvm.array1d.integer

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString

class JvmMutableDoubleArray1D(
        val data: DoubleArray
): MutableDoubleArray1D {

    override val size: Int get() = data.size

    override fun setDouble(value: Double, index: Int) {
        data[index] = value
    }

    override fun getDouble(index: Int): Double = data[index]

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}