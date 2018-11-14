package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.*

abstract class DefaultArrayND<T>: ArrayND<T> {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is ArrayND<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableArrayND<T>: DefaultArrayND<T>(), MutableArrayND<T>
