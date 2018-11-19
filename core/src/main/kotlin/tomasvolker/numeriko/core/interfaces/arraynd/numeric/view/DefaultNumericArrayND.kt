package tomasvolker.numeriko.core.interfaces.arraynd.numeric.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

abstract class DefaultNumericArrayND<N: Number>: NumericArrayND<N> {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is NumericArrayND<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableNumericArrayND<N: Number>: DefaultNumericArrayND<N>(), MutableNumericArrayND<N>

