package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.*

abstract class DefaultArrayND<T>: ArrayND<T> {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableArrayND<T>: DefaultArrayND<T>(), MutableArrayND<T>
