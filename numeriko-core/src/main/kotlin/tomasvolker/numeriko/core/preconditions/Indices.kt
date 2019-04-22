package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun ArrayND<*>.requireValidIndexRange(i: IntProgression, axis: Int) {

    if (NumerikoConfig.checkRanges) {
        // Do not use `indices` as inlining is not working
        if (i.first !in 0 until shape(axis))
            throw IndexOutOfBoundsException("Index ${i.first} in axis $axis is out of shape $shape")
        if (i.last  !in 0 until shape(axis))
            throw IndexOutOfBoundsException("Index ${i.last} in axis $axis is out of shape $shape")
    }

}

fun ArrayND<*>.requireValidIndices(indices: Array<out IntProgression>) {

    if (NumerikoConfig.checkRanges) {

        if (indices.size != rank)
            throw IllegalArgumentException("Indices [${indices.joinToString()}] are invalid for shape $shape")

        for (axis in 0 until rank) {
            val indexProgression = indices[axis]
            // Do not use `indices(axis)` as inlining is not working
            if (indexProgression.first !in 0 until shape(axis))
                throw IndexOutOfBoundsException("Indices [${indexProgression.joinToString()}] are out of range for shape $shape")
            if (indexProgression.last !in 0 until shape(axis))
                throw IndexOutOfBoundsException("Indices [${indexProgression.joinToString()}] are out of range for shape $shape")
        }

    }

}

fun ArrayND<*>.requireValidIndices(indices: IntArray) {

    if (NumerikoConfig.checkRanges) {

        if (indices.size != rank)
            throw IllegalArgumentException("Indices [${indices.joinToString()}] are invalid for shape $shape")

        for (axis in 0 until rank) {
            // Do not use `indices(axis)` as inlining is not working
            if (indices[axis] !in 0 until shape(axis))
                throw IndexOutOfBoundsException("Indices [${indices.joinToString()}] are out of range for shape $shape")
        }

    }

}

fun ArrayND<*>.requireValidIndices(i0: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(1)
        requireValidIndex(i0, 0)
    }

}

fun ArrayND<*>.requireValidIndices(i0: Int, i1: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(2)
        requireValidIndex(i0, 0)
        requireValidIndex(i1, 1)
    }

}

fun ArrayND<*>.requireValidIndices(i0: Int, i1: Int, i2: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(3)
        requireValidIndex(i0, 0)
        requireValidIndex(i1, 1)
        requireValidIndex(i2, 2)
    }

}

fun ArrayND<*>.requireValidIndices(i0: Int, i1: Int, i2: Int, i3: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(4)
        requireValidIndex(i0, 0)
        requireValidIndex(i1, 1)
        requireValidIndex(i2, 2)
        requireValidIndex(i3, 3)
    }

}

fun ArrayND<*>.requireValidIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(5)
        requireValidIndex(i0, 0)
        requireValidIndex(i1, 1)
        requireValidIndex(i2, 2)
        requireValidIndex(i3, 3)
        requireValidIndex(i4, 4)
    }

}

fun ArrayND<*>.requireValidIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int) {

    if (NumerikoConfig.checkRanges) {
        requireRank(6)
        requireValidIndex(i0, 0)
        requireValidIndex(i1, 1)
        requireValidIndex(i2, 2)
        requireValidIndex(i3, 3)
        requireValidIndex(i4, 4)
        requireValidIndex(i5, 5)
    }

}