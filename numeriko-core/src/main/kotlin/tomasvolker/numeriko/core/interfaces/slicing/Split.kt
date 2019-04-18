package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun splitPermutedSlices(array: ArrayND<*>, axis: Int, index: Int): Pair<PermutedSlice, PermutedSlice> {
    require(axis in 0 until array.rank) {
        "axis $axis is not valid for array of rank ${array.rank}"
    }
    require(index in 0..array.shape(axis)) {
        "index $index is out of bounds of size ${array.shape(axis)} in axis $axis"
    }
    return PermutedSlice(
            permutation = IntArray(array.rank) { a -> a },
            shape = IntArray(array.rank) { a -> if (a == axis) index else array.shape(a) },
            strides = IntArray(array.rank) { 1 },
            origin = IntArray(array.rank) { a -> 0 }
    ) to PermutedSlice(
            permutation = IntArray(array.rank) { a -> a },
            shape = IntArray(array.rank) { a -> if (a == axis) array.shape(a) - index else array.shape(a) },
            strides = IntArray(array.rank) { 1 },
            origin = IntArray(array.rank) { a -> if (a == axis) index else 0 }
    )
}


fun <T> ArrayND<T>.split(axis: Int, index: Int): Pair<ArrayND<T>, ArrayND<T>> {
    val slices = splitPermutedSlices(this, axis, index)
    return getPermutedSlice(slices.first) to getPermutedSlice(slices.second)
}

fun DoubleArrayND.split(axis: Int, index: Int): Pair<DoubleArrayND, DoubleArrayND> {
    val slices = splitPermutedSlices(this, axis, index)
    return getPermutedSlice(slices.first) to getPermutedSlice(slices.second)
}

fun IntArrayND.split(axis: Int, index: Int): Pair<IntArrayND, IntArrayND> {
    val slices = splitPermutedSlices(this, axis, index)
    return getPermutedSlice(slices.first) to getPermutedSlice(slices.second)
}

fun FloatArrayND.split(axis: Int, index: Int): Pair<FloatArrayND, FloatArrayND> {
    val slices = splitPermutedSlices(this, axis, index)
    return getPermutedSlice(slices.first) to getPermutedSlice(slices.second)
}
