package numeriko.complex.interfaces.array0d

import tomasvolker.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultComplexArray0D: ComplexArray0D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ComplexArray0D -> this.get() == other.get()
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = get().hashCode()

    override fun toString(): String = get().toString()

}

abstract class DefaultMutableComplexArray0D: DefaultComplexArray0D(), MutableComplexArray0D
