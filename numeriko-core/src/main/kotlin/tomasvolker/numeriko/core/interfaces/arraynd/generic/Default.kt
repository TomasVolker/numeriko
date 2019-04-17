package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice

fun ArrayND<*>.defaultEquals(other: ArrayND<*>): Boolean {

    if(this.shape != other.shape)
        return false

    fastForEachIndices { indices ->
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
                    .map { i -> arrayAlongAxis(axis = 0, index = i) }
                    .joinToString(
                            separator = ", \n",
                            prefix = "[ ",
                            postfix = " ]"
                    )
        }

fun <T> ArrayND<T>.arrayAlongAxis(axis: Int, index: Int): ArrayND<T> =
        getPermutationSlice(
                PermutedSlice(
                        permutation = IntArray(rank-1) { a -> if (a < axis) a else a+1 },
                        shape = IntArray(rank-1) { a -> if (a < axis) shape(a) else shape(a+1) },
                        strides = IntArray(rank-1) { 1 },
                        origin = IntArray(rank) { a -> if (a == axis) index else 0 }
                )
        )
