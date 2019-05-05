package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.generic.GenericArrayNDContext
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayNDContext

fun <A: ArrayND<*>> ArrayNDContext<A>.reduceRank(array: A, axis: Int): A {
    val rank = array.rank
    require(axis in 0 until rank) { "axis $axis is out of bounds for rank $rank" }
    require(array.shape(axis) == 1) { "size on axis $axis is not 1 (${array.shape(axis)})" }
    return slice(
            array = array,
            slice = array.arraySlice(
                shape = IntArray(rank-1) { a ->
                    when {
                        a < axis -> array.shape(a)
                        else -> array.shape(a+1)
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
    )
}


fun <T> ArrayND<T>.reduceRank(axis: Int = 0): ArrayND<T>    = GenericArrayNDContext<T>().reduceRank(this, axis)
fun IntArrayND    .reduceRank(axis: Int = 0): IntArrayND    = IntArrayNDContext.reduceRank(this, axis)
fun DoubleArrayND .reduceRank(axis: Int = 0): DoubleArrayND = DoubleArrayNDContext.reduceRank(this, axis)
fun FloatArrayND  .reduceRank(axis: Int = 0): FloatArrayND  = FloatArrayNDContext.reduceRank(this, axis)
fun ByteArrayND   .reduceRank(axis: Int = 0): ByteArrayND   = ByteArrayNDContext.reduceRank(this, axis)
