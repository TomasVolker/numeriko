package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun ArrayND<*>.expandDimSlice(axis: Int): ArraySlice {
    require(axis in 0..rank) { "axis $axis is out of bounds for rank $rank" }

    return arraySlice(
            shape = IntArray(rank+1) { a ->
                when {
                    a < axis -> shape(a)
                    a == axis -> 1
                    else -> shape(a-1)
                }
            },
            strides = IntArray(rank+1) { 1 },
            permutation = IntArray(rank+1) { a ->
                when {
                    a < axis -> a
                    a == axis -> -1
                    else -> a-1
                }
            }
    )
}


fun <T> ArrayND<T>.expandRank(axis: Int = 0): ArrayND<T>    = getSlice(expandDimSlice(axis))
fun IntArrayND    .expandRank(axis: Int = 0): IntArrayND    = getSlice(expandDimSlice(axis))
fun DoubleArrayND .expandRank(axis: Int = 0): DoubleArrayND = getSlice(expandDimSlice(axis))
fun FloatArrayND  .expandRank(axis: Int = 0): FloatArrayND  = getSlice(expandDimSlice(axis))
