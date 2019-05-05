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

fun <A: ArrayND<*>> ArrayNDContext<A>.expandRank(array: A, axis: Int): A {
    val rank = array.rank
    require(axis in 0..rank) { "axis $axis is out of bounds for rank $rank" }

    return slice(
            array = array,
            slice = array.arraySlice(
                    shape = IntArray(rank+1) { a ->
                        when {
                            a < axis -> array.shape(a)
                            a == axis -> 1
                            else -> array.shape(a-1)
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
    )
}


fun <T> ArrayND<T>.expandRank(axis: Int = 0): ArrayND<T>    = GenericArrayNDContext<T>().expandRank(this, axis)
fun IntArrayND    .expandRank(axis: Int = 0): IntArrayND    = IntArrayNDContext.expandRank(this, axis)
fun DoubleArrayND .expandRank(axis: Int = 0): DoubleArrayND = DoubleArrayNDContext.expandRank(this, axis)
fun FloatArrayND  .expandRank(axis: Int = 0): FloatArrayND  = FloatArrayNDContext.expandRank(this, axis)
fun ByteArrayND   .expandRank(axis: Int = 0): ByteArrayND   = ByteArrayNDContext.expandRank(this, axis)
