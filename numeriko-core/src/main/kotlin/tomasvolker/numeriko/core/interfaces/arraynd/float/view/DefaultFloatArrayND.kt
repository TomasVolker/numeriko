package tomasvolker.numeriko.core.interfaces.arraynd.float.view

import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.defaultHashCode
import tomasvolker.numeriko.core.interfaces.arraynd.float.defaultToString
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals

abstract class DefaultFloatArrayND: FloatArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is FloatArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableFloatArrayND: DefaultFloatArrayND(), MutableFloatArrayND

