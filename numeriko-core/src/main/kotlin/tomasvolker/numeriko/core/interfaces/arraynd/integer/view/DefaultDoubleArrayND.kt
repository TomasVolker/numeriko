package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.arraynd.double.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals

abstract class DefaultDoubleArrayND: DoubleArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is DoubleArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableDoubleArrayND: DefaultDoubleArrayND(), MutableDoubleArrayND

