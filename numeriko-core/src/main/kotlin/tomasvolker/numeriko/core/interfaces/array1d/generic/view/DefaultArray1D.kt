package tomasvolker.numeriko.core.interfaces.array1d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultArray1D<out T>: Array1D<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is Array1D<*> -> this.defaultEquals(other)
        other is ArrayND<*> -> other.rank == 1 && this.defaultEquals(other.as1D())
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableArray1D<T>: DefaultArray1D<T>(), MutableArray1D<T>
