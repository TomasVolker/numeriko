package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
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

private fun resultShape(
        array1: ArrayND<*>,
        array2: ArrayND<*>,
        axis: Int
): IntArray1D {
    requireSameRank(array1, array2)
    array1.requireValidAxis(axis)
    return intArray1D(array1.rank) { a ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }
}

fun <T> concatenate(
        array1: ArrayND<T>,
        array2: ArrayND<T>,
        axis: Int = 0
): ArrayND<T> {
    val resultShape = resultShape(array1, array2, axis)

    val result = arrayNDOfNulls<T>(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result as ArrayND<T>
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

fun concatenate(
        array1: IntArrayND,
        array2: IntArrayND,
        axis: Int = 0
): IntArrayND {
    val resultShape = resultShape(array1, array2, axis)

    val result = intZeros(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}

fun concatenate(
        array1: DoubleArrayND,
        array2: DoubleArrayND,
        axis: Int = 0
): DoubleArrayND {
    val resultShape = resultShape(array1, array2, axis)

    val result = doubleZeros(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}

fun concatenate(
        array1: FloatArrayND,
        array2: FloatArrayND,
        axis: Int = 0
): FloatArrayND {
    val resultShape = resultShape(array1, array2, axis)

    val result = floatZeros(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}

fun concatenate(
        array1: ByteArrayND,
        array2: ByteArrayND,
        axis: Int = 0
): ByteArrayND {
    val resultShape = resultShape(array1, array2, axis)

    val result = byteZeros(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}
