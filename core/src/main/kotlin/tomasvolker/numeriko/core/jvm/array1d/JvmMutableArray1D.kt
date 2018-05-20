package tomasvolker.numeriko.core.jvm.array1d

import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.interfaces.generic.util.defaultEquals
import tomasvolker.numeriko.core.interfaces.generic.util.defaultHashCode
import tomasvolker.numeriko.core.interfaces.generic.util.defaultToString

class JvmMutableArray1D<T>(
        val data: Array<T>
): MutableArray1D<T> {

    override val size: Int get() = data.size

    override fun setValue(value: T, index: Int) {
        data[index] = value
    }

    override fun getValue(index: Int): T = data[index]

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array1D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}