package numeriko.complex.interfaces.array2d.view

import numeriko.complex.interfaces.array2d.ComplexArray2D
import numeriko.complex.interfaces.array2d.MutableComplexArray2D
import numeriko.lowrank.interfaces.array2d.generic.Array2D
import numeriko.lowrank.interfaces.array2d.generic.defaultEquals
import numeriko.lowrank.interfaces.array2d.generic.defaultHashCode
import numeriko.lowrank.interfaces.array2d.generic.defaultToString


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
