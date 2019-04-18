package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun arrayAlongAxisPermutedSlice(array: ArrayND<*>, axis: Int, index: Int): PermutedSlice {
    require(axis in 0 until array.rank) {
        "axis $axis is not valid for array of rank ${array.rank}"
    }
    require(index in 0 until array.shape(axis)) {
        "index $index is out of bounds of size ${array.shape(axis)} in axis $axis"
    }
    return PermutedSlice(
            permutation = IntArray(array.rank-1) { a -> if (a < axis) a else a+1 },
            shape = IntArray(array.rank-1) { a -> if (a < axis) array.shape(a) else array.shape(a+1) },
            strides = IntArray(array.rank-1) { 1 },
            origin = IntArray(array.rank) { a -> if (a == axis) index else 0 }
    )
}


fun <T> ArrayND<T>.arrayAlongAxis(axis: Int, index: Int): ArrayND<T> =
        getPermutedSlice(
                arrayAlongAxisPermutedSlice(this, axis, index)
        )


fun DoubleArrayND.arrayAlongAxis(axis: Int, index: Int): DoubleArrayND =
        getPermutedSlice(
                arrayAlongAxisPermutedSlice(this, axis, index)
        )

fun IntArrayND.arrayAlongAxis(axis: Int, index: Int): IntArrayND =
        getPermutedSlice(
                arrayAlongAxisPermutedSlice(this, axis, index)
        )

fun FloatArrayND.arrayAlongAxis(axis: Int, index: Int): FloatArrayND =
        getPermutedSlice(
                arrayAlongAxisPermutedSlice(this, axis, index)
        )
