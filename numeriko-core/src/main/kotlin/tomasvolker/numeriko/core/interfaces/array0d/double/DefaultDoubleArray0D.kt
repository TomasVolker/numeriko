package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray0D: DoubleArray0D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArray0D -> other.get() == this.get()
        other is DoubleArrayND -> other.rank == 0 && other.getDouble() == this.get()
        other is Array0D<*> -> other.getValue() == this.getDouble()
        other is ArrayND<*> -> other.rank == 0 && other.getValue() == this.getValue()
        else -> false
    }

    override fun hashCode(): Int = get().hashCode()

    override fun toString(): String = get().toString()

}

abstract class DefaultMutableDoubleArray0D: DefaultDoubleArray0D(), MutableDoubleArray0D
