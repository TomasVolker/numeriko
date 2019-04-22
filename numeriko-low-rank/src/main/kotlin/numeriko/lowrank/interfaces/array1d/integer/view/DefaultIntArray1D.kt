package numeriko.lowrank.interfaces.array1d.integer.view

import numeriko.lowrank.interfaces.array1d.generic.Array1D
import numeriko.lowrank.interfaces.array1d.generic.defaultEquals
import numeriko.lowrank.interfaces.array1d.generic.defaultToString
import numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import numeriko.lowrank.interfaces.array1d.integer.MutableIntArray1D
import numeriko.lowrank.interfaces.array1d.integer.defaultEquals
import numeriko.lowrank.interfaces.array1d.integer.defaultHashCode
import tomasvolker.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultIntArray1D: IntArray1D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is IntArray1D -> defaultEquals(this, other)
        other is Array1D<*> -> this.defaultEquals(other)
        other is ArrayND<*> -> other.rank == 1 && this.defaultEquals(other.as1D())
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableIntArray1D: DefaultIntArray1D(), MutableIntArray1D
