package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun ArrayND<*>.splitSlices(axis: Int, index: Int): Pair<ArraySlice, ArraySlice> {
    val rank = rank
    require(axis in 0 until rank) {
        "axis $axis is not valid for array of rank $rank"
    }
    require(index in 0..shape(axis)) {
        "index $index is out of bounds of size ${shape(axis)} in axis $axis"
    }
    return arraySlice(
            shape = IntArray(rank) { a -> if (a == axis) index else shape(a) }
    ) to arraySlice(
            origin = IntArray(rank) { a -> if (a == axis) index else 0 },
            shape = IntArray(rank) { a -> if (a == axis) shape(a) - index else shape(a) }
    )
}


fun <T> ArrayND<T>.split(axis: Int, index: Int): Pair<ArrayND<T>, ArrayND<T>> {
    val slices = splitSlices(axis, index)
    return getSlice(slices.first) to getSlice(slices.second)
}

fun IntArrayND.split(axis: Int, index: Int): Pair<IntArrayND, IntArrayND> {
    val slices = splitSlices(axis, index)
    return getSlice(slices.first) to getSlice(slices.second)
}

fun DoubleArrayND.split(axis: Int, index: Int): Pair<DoubleArrayND, DoubleArrayND> {
    val slices = splitSlices(axis, index)
    return getSlice(slices.first) to getSlice(slices.second)
}

fun FloatArrayND.split(axis: Int, index: Int): Pair<FloatArrayND, FloatArrayND> {
    val slices = splitSlices(axis, index)
    return getSlice(slices.first) to getSlice(slices.second)
}
