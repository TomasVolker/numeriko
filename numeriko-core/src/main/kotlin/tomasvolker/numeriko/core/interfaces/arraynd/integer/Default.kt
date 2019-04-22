package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

abstract class DefaultIntArrayND: IntArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is IntArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableIntArrayND: DefaultIntArrayND(), MutableIntArrayND

fun IntArrayND.defaultEquals(other: IntArrayND): Boolean {

    if (this.rank == 1 && other.rank == 1) {
        if (this.size != other.size) return false

    } else {
        if(this.shape != other.shape) return false
    }

    unsafeForEachIndex { indices ->
        if (this.get(*indices) != other.get(*indices))
            return false
    }

    return true
}

fun IntArrayND.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.toIntArray().hashCode()
    for (x in this) {
        result += 31 * result + x.hashCode()
    }

    return result
}

fun IntArrayND.defaultToString(): String =
        when(rank) {
            0 -> get().toString()
            1 -> joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]"
            )
            else -> (0 until shape[0])
                    .map { alongAxis(axis = 0, index = it) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }
