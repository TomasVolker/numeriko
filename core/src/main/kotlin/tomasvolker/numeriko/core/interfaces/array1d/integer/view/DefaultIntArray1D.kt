package tomasvolker.numeriko.core.interfaces.array1d.integer.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultHashCode

abstract class DefaultIntArray1D: IntArray1D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is IntArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableIntArray1D: DefaultIntArray1D(), MutableIntArray1D
