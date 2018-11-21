package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.*

abstract class DefaultArrayND<T>: ArrayND<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ArrayND<*> -> defaultEquals(this, other)
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableArrayND<T>: DefaultArrayND<T>(), MutableArrayND<T>
