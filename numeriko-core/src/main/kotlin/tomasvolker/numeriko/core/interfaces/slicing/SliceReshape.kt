package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.preconditions.illegalArgument

fun ArrayND<*>.canSliceReshapeTo(shape: IntArray1D): Boolean =
        this.size == shape.product() &&
        (isEmpty() || this.shape.filter { it != 1 } == shape.filter { it != 1 })


fun ArrayND<*>.sliceReshapeSlice(shape: IntArray1D): ArraySlice {
    if (!canSliceReshapeTo(shape)) illegalArgument("Cannot slice reshape ${this.shape} to $shape")

    if(isEmpty())
        return arraySlice(
                origin = IntArray(rank) { 0 },
                shape = shape.toIntArray(),
                strides = IntArray(shape.size) { 1 },
                permutation = IntArray(shape.size) { -1 }
        )

    val thisIndices = this.shape.withIndex().filter { it.value != 1 }.map { it.index }
    val shapeIndices = shape.withIndex().filter { it.value != 1 }.map { it.index }

    val permutation = IntArray(shape.size) { -1 }

    shapeIndices.zip(thisIndices) { shapeIndex, thisIndex ->
        permutation[shapeIndex] = thisIndex
    }

    return arraySlice(
            origin = IntArray(rank) { 0 },
            shape = shape.toIntArray(),
            strides = IntArray(shape.size) { 1 },
            permutation = permutation
    )
}


fun <T> ArrayND<T>.sliceReshape(shape: IntArray1D): ArrayND<T>    = getSlice(sliceReshapeSlice(shape))
fun IntArrayND    .sliceReshape(shape: IntArray1D): IntArrayND    = getSlice(sliceReshapeSlice(shape))
fun DoubleArrayND .sliceReshape(shape: IntArray1D): DoubleArrayND = getSlice(sliceReshapeSlice(shape))
fun FloatArrayND  .sliceReshape(shape: IntArray1D): FloatArrayND  = getSlice(sliceReshapeSlice(shape))

