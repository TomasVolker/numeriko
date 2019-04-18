package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.slicing.split

infix fun <T> ArrayND<T>.concat(other: ArrayND<T>): ArrayND<T> =
        concatenate(this, other, axis = 0)

infix fun IntArrayND.concat(other: IntArrayND): IntArrayND =
        concatenate(this, other, axis = 0)

infix fun DoubleArrayND.concat(other: DoubleArrayND): DoubleArrayND =
        concatenate(this, other, axis = 0)

infix fun FloatArrayND.concat(other: FloatArrayND): FloatArrayND =
        concatenate(this, other, axis = 0)

fun <T> concatenate(
        array1: ArrayND<T>,
        array2: ArrayND<T>,
        axis: Int = 0
): ArrayND<T> {
    require(array1.rank == array2.rank) { "arrays are not of the same rank (${array1.rank} and ${array2.rank})" }
    require(axis in 0 until array1.rank) { "axis $axis is out of bound for rank ${array1.rank}" }

    val resultShape = unsafeIntArrayND(I[array1.rank]) { (a) ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }

    val result = arrayNDOfNulls<T>(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result as ArrayND<T>
}

fun concatenate(
        array1: IntArrayND,
        array2: IntArrayND,
        axis: Int = 0
): IntArrayND {
    require(array1.rank == array2.rank) { "arrays are not of the same rank (${array1.rank} and ${array2.rank})" }
    require(axis in 0 until array1.rank) { "axis $axis is out of bound for rank ${array1.rank}" }

    val resultShape = unsafeIntArrayND(I[array1.rank]) { (a) ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }

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
    require(array1.rank == array2.rank) { "arrays are not of the same rank (${array1.rank} and ${array2.rank})" }
    require(axis in 0 until array1.rank) { "axis $axis is out of bound for rank ${array1.rank}" }

    val resultShape = unsafeIntArrayND(I[array1.rank]) { (a) ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }

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
    require(array1.rank == array2.rank) { "arrays are not of the same rank (${array1.rank} and ${array2.rank})" }
    require(axis in 0 until array1.rank) { "axis $axis is out of bound for rank ${array1.rank}" }

    val resultShape = unsafeIntArrayND(I[array1.rank]) { (a) ->
        when {
            a == axis -> array1.shape(a) + array2.shape(a)
            else -> array1.shape(a)
        }
    }

    val result = floatZeros(resultShape)

    val (first, second) = result.split(axis, array1.shape(axis))

    first.asMutable().setValue(array1)
    second.asMutable().setValue(array2)

    return result
}
