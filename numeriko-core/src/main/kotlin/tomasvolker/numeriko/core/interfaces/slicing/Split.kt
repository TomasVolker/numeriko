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

fun <A: ArrayND<*>> ArrayNDContext<A>.split(array: A, axis: Int, index: Int): Pair<A, A> {
    val rank = array.rank
    require(axis in 0 until rank) {
        "axis $axis is not valid for array of rank $rank"
    }
    require(index in 0..array.shape(axis)) {
        "index $index is out of bounds of size ${array.shape(axis)} in axis $axis"
    }
    val first = slice(
            array = array,
            slice = array.arraySlice(
                    shape = IntArray(rank) { a -> if (a == axis) index else array.shape(a) }
            )
    )
    val second = slice(
            array = array,
            slice = array.arraySlice(
                origin = IntArray(rank) { a -> if (a == axis) index else 0 },
                shape = IntArray(rank) { a -> if (a == axis) array.shape(a) - index else array.shape(a) }
            )
    )
    return Pair(first, second)
}


fun <T> ArrayND<T>.split(axis: Int, index: Int): Pair<ArrayND<T>, ArrayND<T>> =
        GenericArrayNDContext<T>().split(this, axis, index)

fun IntArrayND.split(axis: Int, index: Int): Pair<IntArrayND, IntArrayND> =
        IntArrayNDContext.split(this, axis, index)

fun DoubleArrayND.split(axis: Int, index: Int): Pair<DoubleArrayND, DoubleArrayND> =
        DoubleArrayNDContext.split(this, axis, index)

fun FloatArrayND.split(axis: Int, index: Int): Pair<FloatArrayND, FloatArrayND> =
        FloatArrayNDContext.split(this, axis, index)

fun ByteArrayND.split(axis: Int, index: Int): Pair<ByteArrayND, ByteArrayND> =
        ByteArrayNDContext.split(this, axis, index)