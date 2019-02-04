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
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableComplexArrayND: DefaultComplexArrayND(), MutableComplexArrayND

