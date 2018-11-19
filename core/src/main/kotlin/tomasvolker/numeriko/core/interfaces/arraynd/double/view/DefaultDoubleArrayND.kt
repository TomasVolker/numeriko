package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.*
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

abstract class DefaultDoubleArrayND: DoubleArrayND {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArrayND) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArrayND: DefaultDoubleArrayND(), MutableDoubleArrayND

