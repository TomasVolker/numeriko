package numeriko.complex.interfaces.arraynd.view

import numeriko.complex.interfaces.arraynd.ComplexArrayND
import numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.core.interfaces.arraynd.generic.ArrayND

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

