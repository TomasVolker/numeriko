package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun expandDimPermutedSlice(array: ArrayND<*>, axis: Int): PermutedSlice {
    require(axis in 0..array.rank) { "axis $axis is out of bounds for rank ${array.rank}" }

    return PermutedSlice(
            permutation = IntArray(array.rank+1) { a ->
                when {
                    a < axis -> a
                    a == axis -> -1
                    else -> a-1
                }
            },
            shape = IntArray(array.rank+1) { a ->
                when {
                    a < axis -> array.shape(a)
                    a == axis -> 1
                    else -> array.shape(a-1)
                }
            },
            strides = IntArray(array.rank+1) { 1 },
            origin = IntArray(array.rank) { 0 }
    )
}


fun <T> ArrayND<T>.expandDim(axis: Int = 0): ArrayND<T> =
        getPermutedSlice(
                expandDimPermutedSlice(this, axis)
        )


fun DoubleArrayND.arrayAlongAxis(axis: Int = 0): DoubleArrayND =
        getPermutedSlice(
                expandDimPermutedSlice(this, axis)
        )

fun IntArrayND.arrayAlongAxis(axis: Int = 0): IntArrayND =
        getPermutedSlice(
                expandDimPermutedSlice(this, axis)
        )

fun FloatArrayND.arrayAlongAxis(axis: Int = 0): FloatArrayND =
        getPermutedSlice(
                expandDimPermutedSlice(this, axis)
        )
