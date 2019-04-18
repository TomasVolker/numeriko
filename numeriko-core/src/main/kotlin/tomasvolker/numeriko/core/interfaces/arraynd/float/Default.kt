package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.slicing.arrayAlongAxis

fun FloatArrayND.defaultEquals(other: FloatArrayND): Boolean {

    if(this.shape != other.shape)
        return false

    fastForEachIndices { indices ->
        if (get(*indices) != other.get(*indices))
            return false
    }

    return true
}

fun FloatArrayND.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
    for (x in this) {
        result += 31 * result + x.hashCode()
    }

    return result
}

fun FloatArrayND.defaultToString(): String =
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
