package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString

abstract class DefaultDoubleArray1D: DoubleArray1D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray1D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArray1D: DefaultDoubleArray1D(), MutableDoubleArray1D
