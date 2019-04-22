package numeriko.complex.interfaces.array1d.view

import numeriko.complex.interfaces.array1d.ComplexArray1D
import numeriko.complex.interfaces.array1d.MutableComplexArray1D
import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import numeriko.lowrank.interfaces.array1d.generic.defaultEquals
import numeriko.lowrank.interfaces.array1d.generic.defaultHashCode
import numeriko.lowrank.interfaces.array1d.generic.defaultToString

abstract class DefaultComplexArray1D: ComplexArray1D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return this.defaultEquals(other)
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableComplexArray1D: DefaultComplexArray1D(), MutableComplexArray1D
