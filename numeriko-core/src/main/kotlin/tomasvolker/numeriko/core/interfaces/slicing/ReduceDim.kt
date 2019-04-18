package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun reduceDimPermutedSlice(array: ArrayND<*>, axis: Int): PermutedSlice {
    require(axis in 0 until array.rank) { "axis $axis is out of bounds for rank ${array.rank}" }
    require(array.shape(axis) == 1) { "size on axis $axis is not 1 (${array.shape(axis)})" }

    return PermutedSlice(
            permutation = IntArray(array.rank-1) { a ->
                when {
                    a < axis -> a
                    else -> a+1
                }
            },
            shape = IntArray(array.rank-1) { a ->
                when {
                    a < axis -> array.shape(a)
                    else -> array.shape(a+1)
                }
            },
            strides = IntArray(array.rank-1) { 1 },
            origin = IntArray(array.rank) { 0 }
    )
}


fun <T> ArrayND<T>.reduceDim(axis: Int = 0): ArrayND<T> =
        getPermutedSlice(
                reduceDimPermutedSlice(this, axis)
        )


fun DoubleArrayND.reduceDim(axis: Int = 0): DoubleArrayND =
        getPermutedSlice(
                reduceDimPermutedSlice(this, axis)
        )

fun IntArrayND.reduceDim(axis: Int = 0): IntArrayND =
        getPermutedSlice(
                reduceDimPermutedSlice(this, axis)
        )

fun FloatArrayND.reduceDim(axis: Int = 0): FloatArrayND =
        getPermutedSlice(
                reduceDimPermutedSlice(this, axis)
        )
