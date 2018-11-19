package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

abstract class DefaultDoubleArray0D: DoubleArray0D {

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is ArrayND<*>) return false
        return other.rank == 0 && other.getValue() == this.get()
    }

    override fun hashCode(): Int = getValue().hashCode()

    override fun toString(): String = getValue().toString()

}

abstract class DefaultMutableDoubleArray0D: DefaultDoubleArray0D(), MutableDoubleArray0D
