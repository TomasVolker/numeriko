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

fun <A: ArrayND<*>> ArrayNDContext<A>.alongAxis(array: A, axis: Int, index: Int): A {
    val rank = array.rank
    require(axis in 0 until rank) {
        "axis $axis is not valid for array of rank $rank"
    }
    require(index in 0 until array.shape(axis)) {
        "index $index is out of bounds of size ${array.shape(axis)} in axis $axis"
    }

    return slice(
            array,
            ArraySlice(
                    origin = IntArray(rank) { a -> if (a == axis) index else 0 },
                    shape = IntArray(rank -1) { a -> if (a < axis) array.shape(a) else array.shape(a+1) },
                    strides = IntArray(rank -1) { 1 },
                    permutation = IntArray(rank -1) { a -> if (a < axis) a else a+1 }
            )
    )
}

fun <T> ArrayND<T>.alongAxis(axis: Int, index: Int): ArrayND<T>    = GenericArrayNDContext<T>().alongAxis(this, axis, index)
fun IntArrayND    .alongAxis(axis: Int, index: Int): IntArrayND    = IntArrayNDContext.alongAxis(this, axis, index)
fun DoubleArrayND .alongAxis(axis: Int, index: Int): DoubleArrayND = DoubleArrayNDContext.alongAxis(this, axis, index)
fun FloatArrayND  .alongAxis(axis: Int, index: Int): FloatArrayND  = FloatArrayNDContext.alongAxis(this, axis, index)
fun ByteArrayND   .alongAxis(axis: Int, index: Int): ByteArrayND   = ByteArrayNDContext.alongAxis(this, axis, index)
