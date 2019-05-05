package tomasvolker.numeriko.core.operations

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
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.slicing.set
import tomasvolker.numeriko.core.interfaces.slicing.split
import tomasvolker.numeriko.core.preconditions.requireSameRank
import tomasvolker.numeriko.core.preconditions.requireValidAxis

infix fun <T> ArrayND<T>.concat(other: ArrayND<T>   ): ArrayND<T>    = concatenate(this, other, axis = 0)
infix fun IntArrayND    .concat(other: IntArrayND   ): IntArrayND    = concatenate(this, other, axis = 0)
infix fun DoubleArrayND .concat(other: DoubleArrayND): DoubleArrayND = concatenate(this, other, axis = 0)
infix fun FloatArrayND  .concat(other: FloatArrayND ): FloatArrayND  = concatenate(this, other, axis = 0)
infix fun ByteArrayND   .concat(other: ByteArrayND  ): ByteArrayND   = concatenate(this, other, axis = 0)

infix fun IntArray1D    .concat(other: IntArray1D   ): IntArray1D    = concatenate(this, other)

fun <T, A: ArrayND<T>> ArrayNDContext<A>.concatenate(
        array1: A,
        array2: A,
        axis: Int = 0
): A {

    requireSameRank(array1, array2)
    array1.requireValidAxis(axis)
    val resultShape = intArray1D(array1.rank) { a ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }

    val result = buildDefault(resultShape)

    val (first, second) = split(result, axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}

fun concatenate(
        array1: IntArray1D,
        array2: IntArray1D
): IntArray1D {
    val result = intZeros(array1.size + array2.size).asMutable()
    result[0 until array1.size] = array1
    result[array1.size until result.size] = array2
    return result
}

fun <T> concatenate(
        array1: ArrayND<T>,
        array2: ArrayND<T>,
        axis: Int = 0
): ArrayND<T> = GenericArrayNDContext<T>().concatenate(array1, array2, axis)

fun concatenate(
        array1: IntArrayND,
        array2: IntArrayND,
        axis: Int = 0
): IntArrayND = IntArrayNDContext.concatenate(array1, array2, axis)

fun concatenate(
        array1: DoubleArrayND,
        array2: DoubleArrayND,
        axis: Int = 0
): DoubleArrayND = DoubleArrayNDContext.concatenate(array1, array2, axis)

fun concatenate(
        array1: FloatArrayND,
        array2: FloatArrayND,
        axis: Int = 0
): FloatArrayND = FloatArrayNDContext.concatenate(array1, array2, axis)

fun concatenate(
        array1: ByteArrayND,
        array2: ByteArrayND,
        axis: Int = 0
): ByteArrayND = ByteArrayNDContext.concatenate(array1, array2, axis)
