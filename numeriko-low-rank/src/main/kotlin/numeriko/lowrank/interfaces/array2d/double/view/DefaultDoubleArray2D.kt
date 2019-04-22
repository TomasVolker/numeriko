package numeriko.lowrank.interfaces.array2d.double.view

import numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import numeriko.lowrank.interfaces.array2d.double.MutableDoubleArray2D
import numeriko.lowrank.interfaces.array2d.double.defaultEquals
import numeriko.lowrank.interfaces.array2d.double.defaultHashCode
import numeriko.lowrank.interfaces.array2d.generic.Array2D
import numeriko.lowrank.interfaces.array2d.generic.defaultEquals
import numeriko.lowrank.interfaces.array2d.generic.defaultToString
import tomasvolker.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray2D: DoubleArray2D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArray2D -> this.defaultEquals(other)
        other is DoubleArrayND -> other.rank == 2 && this.defaultEquals(other.as2D())
        other is Array2D<*> -> this.defaultEquals(other)
        other is ArrayND<*> -> other.rank == 2 && this.defaultEquals(other.as2D())
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableDoubleArray2D: DefaultDoubleArray2D(), MutableDoubleArray2D
