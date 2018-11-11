package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultToString

abstract class DefaultDoubleArray2D: DoubleArray2D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray2D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArray2D: DefaultDoubleArray2D(), MutableDoubleArray2D
