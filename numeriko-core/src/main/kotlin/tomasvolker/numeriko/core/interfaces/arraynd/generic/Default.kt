package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

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

fun ArrayND<*>.defaultEquals(other: ArrayND<*>): Boolean {

    if(this.shape != other.shape)
        return false

    unsafeForEachIndex { indices ->
        if (this.getValue(indices) != other.getValue(indices))
            return false
    }

    return true
}

fun ArrayND<*>.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
    for (x in this) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun ArrayND<*>.defaultToString(): String =
        when(rank) {
            0 -> getValue().toString()
            1 -> joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]"
            )
            else -> (0 until shape(0))
                    .map { i -> alongAxis(axis = 0, index = i) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }

