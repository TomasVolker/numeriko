package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray1D: DoubleArray1D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArray1D -> defaultEquals(this, other)
        other is DoubleArrayND -> other.rank == 1 && defaultEquals(other.as1D(), this)
        other is Array1D<*> -> defaultEquals(this, other)
        other is ArrayND<*> -> other.rank == 1 && defaultEquals(other.as1D(), this)
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArray1D: DefaultDoubleArray1D(), MutableDoubleArray1D
