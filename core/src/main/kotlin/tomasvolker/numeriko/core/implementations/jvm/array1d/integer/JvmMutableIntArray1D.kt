package tomasvolker.numeriko.core.implementations.jvm.array1d.integer

import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultHashCode

class JvmMutableIntArray1D(
        val data: IntArray
): MutableIntArray1D {

    override val size: Int get() = data.size

    override fun setInt(value: Int, index: Int) {
        data[index] = value
    }

    override fun getInt(index: Int): Int = data[index]

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}