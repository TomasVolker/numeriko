package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
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
import tomasvolker.numeriko.core.preconditions.illegalArgument

fun ArrayND<*>.canSliceReshapeTo(shape: IntArray1D): Boolean =
        this.size == shape.product() &&
        (isEmpty() || this.shape.filter { it != 1 } == shape.filter { it != 1 })


fun <A: ArrayND<*>> ArrayNDContext<A>.sliceReshape(array: A, shape: IntArray1D): A {
    if (!array.canSliceReshapeTo(shape)) illegalArgument("Cannot slice reshape ${array.shape} to $shape")

    if(array.isEmpty())
        return slice(
                array = array,
                slice = array.arraySlice(
                    origin = IntArray(array.rank) { 0 },
                    shape = shape.toIntArray(),
                    strides = IntArray(shape.size) { 1 },
                    permutation = IntArray(shape.size) { -1 }
                )
        )

    val thisIndices = array.shape.withIndex().filter { it.value != 1 }.map { it.index }
    val shapeIndices = shape.withIndex().filter { it.value != 1 }.map { it.index }

    val permutation = IntArray(shape.size) { -1 }

    shapeIndices.zip(thisIndices) { shapeIndex, thisIndex ->
        permutation[shapeIndex] = thisIndex
    }

    return slice(
            array = array,
            slice = array.arraySlice(
                origin = IntArray(array.rank) { 0 },
                shape = shape.toIntArray(),
                strides = IntArray(shape.size) { 1 },
                permutation = permutation
            )
        )
}


fun <T> ArrayND<T>.sliceReshape(shape: IntArray1D): ArrayND<T>    = GenericArrayNDContext<T>().sliceReshape(this, shape)
fun IntArrayND    .sliceReshape(shape: IntArray1D): IntArrayND    = IntArrayNDContext.sliceReshape(this, shape)
fun DoubleArrayND .sliceReshape(shape: IntArray1D): DoubleArrayND = DoubleArrayNDContext.sliceReshape(this, shape)
fun FloatArrayND  .sliceReshape(shape: IntArray1D): FloatArrayND  = FloatArrayNDContext.sliceReshape(this, shape)
fun ByteArrayND   .sliceReshape(shape: IntArray1D): ByteArrayND   = ByteArrayNDContext.sliceReshape(this, shape)

