package tomasvolker.numeriko.core.implementations.jvm.array1d.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.*

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