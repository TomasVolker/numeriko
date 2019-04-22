package numeriko.lowrank.interfaces.array2d.generic.view

import numeriko.lowrank.interfaces.array2d.generic.*
import tomasvolker.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultArray2D<out T>: Array2D<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is Array2D<*> -> this.defaultEquals(other)
        other is ArrayND<*> -> other.rank == this.rank && this.defaultEquals(other.as2D())
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableArray2D<T>: DefaultArray2D<T>(), MutableArray2D<T>
