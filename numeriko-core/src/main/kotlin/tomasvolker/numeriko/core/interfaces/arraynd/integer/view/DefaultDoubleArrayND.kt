package tomasvolker.numeriko.core.interfaces.arraynd.integer.view

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.defaultHashCode
import tomasvolker.numeriko.core.interfaces.arraynd.integer.defaultToString

abstract class DefaultIntArrayND: IntArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is IntArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableIntArrayND: DefaultIntArrayND(), MutableIntArrayND

