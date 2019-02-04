package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultToString


abstract class DefaultComplexArray2D: ComplexArray2D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array2D<*>) return false
        return this.defaultEquals(other)
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableComplexArray2D: DefaultComplexArray2D(), MutableComplexArray2D
