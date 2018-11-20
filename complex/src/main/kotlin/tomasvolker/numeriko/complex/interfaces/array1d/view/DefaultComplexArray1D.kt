package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString

abstract class DefaultComplexArray1D: ComplexArray1D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableComplexArray1D: DefaultComplexArray1D(), MutableComplexArray1D
