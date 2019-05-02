package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

private fun List<ArrayND<*>>.resultShape(axis: Int): IntArray1D {
    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    return intArray1D(firstShape.size+1) { a ->
        when {
            a < axis -> firstShape[a]
            a == axis -> size
            else -> firstShape[a-1]
        }
    }
}

fun <T> List<ArrayND<T>>.stack(axis: Int = 0): ArrayND<T> {

    if (isEmpty())
        return arrayND(intArrayOf(0), arrayOf<Any>() as Array<T>)

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = arrayNDOfNulls<T>(resultShape(axis)).asMutable()

    forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result as ArrayND<T>
}

fun List<DoubleArrayND>.stack(axis: Int = 0): DoubleArrayND {

    if (isEmpty())
        return doubleZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = doubleZeros(resultShape(axis)).asMutable()

    forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}

fun List<FloatArrayND>.stack(axis: Int = 0): FloatArrayND {

    if (isEmpty())
        return floatZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = floatZeros(resultShape(axis)).asMutable()

    forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}

fun List<IntArrayND>.stack(axis: Int = 0): IntArrayND {

    if (isEmpty())
        return intZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = intZeros(resultShape(axis)).asMutable()

    forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}

fun List<ByteArrayND>.stack(axis: Int = 0): ByteArrayND {

    if (isEmpty())
        return byteZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = byteZeros(resultShape(axis)).asMutable()

    forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}
