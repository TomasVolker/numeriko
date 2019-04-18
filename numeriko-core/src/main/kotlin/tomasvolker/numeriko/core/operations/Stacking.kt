package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.interfaces.slicing.arrayAlongAxis


fun <T> List<ArrayND<T>>.stack(axis: Int = 0): ArrayND<T> {

    if (isEmpty())
        return arrayND(intArrayOf(0), arrayOf<Any>() as Array<T>)

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = arrayNDOfNulls<T>(
            intArrayND(firstShape.size+1) { (a0) ->
                when {
                    a0 < axis -> firstShape[a0]
                    a0 == axis -> size
                    else -> firstShape[a0-1]
                }
            }
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result as ArrayND<T>
}

fun List<DoubleArrayND>.stack(axis: Int = 0): DoubleArrayND {

    if (isEmpty())
        return doubleZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = doubleZeros(
            intArrayND(firstShape.size+1) { (a0) ->
                when {
                    a0 < axis -> firstShape[a0]
                    a0 == axis -> size
                    else -> firstShape[a0-1]
                }
            }
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}

fun List<FloatArrayND>.stack(axis: Int = 0): FloatArrayND {

    if (isEmpty())
        return floatZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = floatZeros(
            intArrayND(firstShape.size+1) { (a0) ->
                when {
                    a0 < axis -> firstShape[a0]
                    a0 == axis -> size
                    else -> firstShape[a0-1]
                }
            }
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}

fun List<IntArrayND>.stack(axis: Int = 0): IntArrayND {

    if (isEmpty())
        return intZeros(I[0])

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = intZeros(
            intArrayND(firstShape.size+1) { (a0) ->
                when {
                    a0 < axis -> firstShape[a0]
                    a0 == axis -> size
                    else -> firstShape[a0-1]
                }
            }
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result
}
