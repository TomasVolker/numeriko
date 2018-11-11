package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.*

abstract class DefaultArray2D<out T>: Array2D<T> {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray2D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableArray2D<T>: DefaultArray2D<T>(), MutableArray2D<T>
