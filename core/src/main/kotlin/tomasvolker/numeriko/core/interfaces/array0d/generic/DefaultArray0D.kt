package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultArray0D<T>: Array0D<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is Array0D<*> -> other.getValue() == this.getValue()
        other is ArrayND<*> -> other.rank == 0 && other.getValue() == this.getValue()
        else -> false
    }

    override fun hashCode(): Int = getValue().hashCode()

    override fun toString(): String = getValue().toString()

}

abstract class DefaultMutableArray0D<T>: DefaultArray0D<T>(), MutableArray0D<T>
