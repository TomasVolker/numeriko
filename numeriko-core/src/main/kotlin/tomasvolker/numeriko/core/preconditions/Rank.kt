package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun requireSameRank(array1: ArrayND<*>, array2: ArrayND<*>) =
        require(array1.rank == array2.rank) {
            "Arrays must have the same rank (${array1.rank} != ${array2.rank})"
        }

fun requireSameShape(array1: ArrayND<*>, array2: ArrayND<*>) =
        require(array1.shape == array2.shape) {
            "Arrays must have the same shape (${array1.shape} != ${array2.shape})"
        }

fun ArrayND<*>.rankError(target: Int): Nothing =
        throw IllegalArgumentException(
                "Array of rank $rank cannot be viewed as rank ${if (target < 0) "N" else target.toString()}"
        )

fun ArrayND<*>.requireValidAxis(axis: Int) {

    if (NumerikoConfig.checkRanges) {
        // Do not use `axes` as inlining is not working
        if (axis !in 0 until rank)
            throw IndexOutOfBoundsException("Axis index $axis invalid for rank $rank")

    }

}

fun ArrayND<*>.requireValidIndex(i: Int, axis: Int) {
    // Do not use `indices()` as inlining is not working
    require(i in 0 until shape(axis)) {
        "Index $i on axis $axis is out of size ${shape(axis)}"
    }
}

fun ArrayND<*>.requireRank(rank: Int) {
    require(this.rank == rank) {
        "Rank $rank operation requested on a rank ${this.rank} array"
    }

}

