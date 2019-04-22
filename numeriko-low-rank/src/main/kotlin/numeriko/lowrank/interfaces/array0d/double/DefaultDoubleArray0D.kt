package numeriko.lowrank.interfaces.array0d.double

import numeriko.lowrank.interfaces.array0d.generic.Array0D
import tomasvolker.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray0D: DoubleArray0D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArray0D -> other.get() == this.get()
        other is DoubleArrayND -> other.rank == 0 && other.get() == this.get()
        other is Array0D<*> -> other.getValue() == this.get()
        other is ArrayND<*> -> other.rank == 0 && other.getValue() == this.getValue()
        else -> false
    }

    override fun hashCode(): Int = get().hashCode()

    override fun toString(): String = get().toString()

}

abstract class DefaultMutableDoubleArray0D: DefaultDoubleArray0D(), MutableDoubleArray0D
