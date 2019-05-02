package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun ArrayND<*>.alongAxisSlice(axis: Int, index: Int): ArraySlice {
    require(axis in 0 until rank) {
        "axis $axis is not valid for array of rank $rank"
    }
    require(index in 0 until shape(axis)) {
        "index $index is out of bounds of size ${shape(axis)} in axis $axis"
    }
    return arraySlice(
            origin = IntArray(rank) { a -> if (a == axis) index else 0 },
            shape = IntArray(rank -1) { a -> if (a < axis) shape(a) else shape(a+1) },
            strides = IntArray(rank -1) { 1 },
            permutation = IntArray(rank -1) { a -> if (a < axis) a else a+1 }
    )
}


fun <T> ArrayND<T>.alongAxis(axis: Int, index: Int): ArrayND<T>    = getSlice(alongAxisSlice(axis, index))
fun IntArrayND    .alongAxis(axis: Int, index: Int): IntArrayND    = getSlice(alongAxisSlice(axis, index))
fun DoubleArrayND .alongAxis(axis: Int, index: Int): DoubleArrayND = getSlice(alongAxisSlice(axis, index))
fun FloatArrayND  .alongAxis(axis: Int, index: Int): FloatArrayND  = getSlice(alongAxisSlice(axis, index))
fun ByteArrayND   .alongAxis(axis: Int, index: Int): ByteArrayND   = getSlice(alongAxisSlice(axis, index))
