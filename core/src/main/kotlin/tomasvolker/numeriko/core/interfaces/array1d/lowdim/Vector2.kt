package tomasvolker.numeriko.core.interfaces.array1d.lowdim

import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultHashCode

interface Vector2<out T>: Array1D<T> {

    override val size: Int get() = 2

}

class IntVector2(
        val value0: Int,
        val value1: Int
): Vector2<Int>, IntArray1D {

    override fun getInt(index: Int) = when(index) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

    operator fun component1(): Int = value0
    operator fun component2(): Int = value1

}

class MutableIntVector2(
        var value0: Int,
        var value1: Int
): Vector2<Int>, MutableIntArray1D {

    override fun getInt(index: Int) = when(index) {
        0 -> value0
        1 -> value1
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun setInt(value: Int, index: Int) = when(index) {
        0 -> value0 = value
        1 -> value1 = value
        else -> throw IndexOutOfBoundsException(index)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

    operator fun component1(): Int = value0
    operator fun component2(): Int = value1

}