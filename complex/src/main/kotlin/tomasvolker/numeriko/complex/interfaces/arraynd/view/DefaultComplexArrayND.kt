package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultToString

abstract class DefaultComplexArrayND: ComplexArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ArrayND<*> -> defaultEquals(this, other)
        else -> false
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

abstract class DefaultMutableComplexArrayND: DefaultComplexArrayND(), MutableComplexArrayND

