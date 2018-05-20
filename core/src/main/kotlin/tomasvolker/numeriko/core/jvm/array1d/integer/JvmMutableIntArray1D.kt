package tomasvolker.numeriko.core.jvm.array1d.integer

import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString
import tomasvolker.numeriko.core.interfaces.integer.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.integer.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.integer.util.defaultHashCode

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