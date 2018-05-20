package tomasvolker.numeriko.core.jvm.array1d.integer

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.double.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

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