package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.defaultEquals
import tomasvolker.numeriko.core.interfaces.array2d.double.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.defaultToString
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray2D: DoubleArray2D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArray2D -> defaultEquals(this, other)
        other is DoubleArrayND -> other.rank == 2 && defaultEquals(this, other.as2D())
        other is Array2D<*> -> defaultEquals(this, other)
        other is ArrayND<*> -> other.rank == 2 && defaultEquals(this, other.as2D())
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArray2D: DefaultDoubleArray2D(), MutableDoubleArray2D
