package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultArray2D<out T>: Array2D<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is Array2D<*> -> defaultEquals(this, other)
        other is ArrayND<*> -> other.rank == this.rank && defaultEquals(this, other.as2D())
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableArray2D<T>: DefaultArray2D<T>(), MutableArray2D<T>
