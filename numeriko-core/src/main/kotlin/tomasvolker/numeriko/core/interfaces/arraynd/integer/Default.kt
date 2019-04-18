package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.slicing.arrayAlongAxis

fun IntArrayND.defaultEquals(other: IntArrayND): Boolean {

    if (this.rank == 1 && other.rank == 1) {
        if (this.size != other.size) return false

    } else {
        if(this.shape != other.shape) return false
    }

    fastForEachIndices { indices ->
        if (this.get(*indices) != other.get(*indices))
            return false
    }

    return true
}

fun IntArrayND.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
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
                    .map { arrayAlongAxis(axis = 0, index = it) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }
