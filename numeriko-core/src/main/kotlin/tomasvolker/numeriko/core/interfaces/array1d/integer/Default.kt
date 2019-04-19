package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.array1d.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.integer.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.defaultHashCode

abstract class DefaultIntArray1D: IntArray1D {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is IntArray1D -> this.defaultEquals(other)
        other is IntArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableIntArray1D: DefaultIntArray1D(), MutableIntArray1D

fun IntArray1D.defaultEquals(other: IntArray1D): Boolean {

    if(this.size != other.size) return false

    for(i in 0 until size) {
        if (this[i] != other[i]) return false
    }

    return true
}

fun IntArray1D.defaultToString(): String =
        joinToString(
                separator = ", ",
                prefix = "[ ",
                postfix = " ]"
        )
