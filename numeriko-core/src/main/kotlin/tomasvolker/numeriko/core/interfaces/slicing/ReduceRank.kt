package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun ArrayND<*>.reduceRankSlice(axis: Int): ArraySlice {
    require(axis in 0 until rank) { "axis $axis is out of bounds for rank $rank" }
    require(shape(axis) == 1) { "size on axis $axis is not 1 (${shape(axis)})" }
    return arraySlice(
            shape = IntArray(rank-1) { a ->
                when {
                    a < axis -> shape(a)
                    else -> shape(a+1)
                }
            },
            strides = IntArray(rank-1) { 1 },
            permutation = IntArray(rank-1) { a ->
                when {
                    a < axis -> a
                    else -> a+1
                }
            }
    )
}


fun <T> ArrayND<T>.reduceRank(axis: Int = 0): ArrayND<T>    = getSlice(reduceRankSlice(axis))
fun IntArrayND    .reduceRank(axis: Int = 0): IntArrayND    = getSlice(reduceRankSlice(axis))
fun DoubleArrayND .reduceRank(axis: Int = 0): DoubleArrayND = getSlice(reduceRankSlice(axis))
fun FloatArrayND  .reduceRank(axis: Int = 0): FloatArrayND  = getSlice(reduceRankSlice(axis))
fun ByteArrayND   .reduceRank(axis: Int = 0): ByteArrayND   = getSlice(reduceRankSlice(axis))
