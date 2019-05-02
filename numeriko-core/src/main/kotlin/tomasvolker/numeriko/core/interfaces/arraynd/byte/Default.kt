package tomasvolker.numeriko.core.interfaces.arraynd.byte

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

abstract class DefaultByteArrayND: ByteArrayND {

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is ByteArrayND -> this.defaultEquals(other)
        other is ArrayND<*> -> this.defaultEquals(other)
        else -> false
    }

    override fun hashCode(): Int = this.defaultHashCode()

    override fun toString(): String = this.defaultToString()

}

abstract class DefaultMutableByteArrayND: DefaultByteArrayND(), MutableByteArrayND

fun ByteArrayND.defaultEquals(other: ByteArrayND): Boolean {

    if(this.shape != other.shape)
        return false

    unsafeForEachIndex { indices ->
        if (get(*indices) != other.get(*indices))
            return false
    }

    return true
}

fun ByteArrayND.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
    for (x in this) {
        result += 31 * result + x.hashCode()
    }

    return result
}

fun ByteArrayND.defaultToString(): String =
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
