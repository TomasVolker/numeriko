package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.dsl.I
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
import tomasvolker.numeriko.core.interfaces.slicing.alongAxis

fun <T, A: ArrayND<T>> ArrayNDContext<A>.stack(arrayList: List<A>, axis: Int = 0): A {

    if (arrayList.isEmpty())
        return buildDefault(I[0])

    val firstShape = arrayList.first().shape
    require(arrayList.all { it.shape == firstShape }) { "All shapes must be the same" }

    val resultShape = intArray1D(firstShape.size+1) { a ->
        when {
            a < axis -> firstShape[a]
            a == axis -> arrayList.size
            else -> firstShape[a-1]
        }
    }

    val result = buildDefault(resultShape)

    arrayList.forEachIndexed { i, array ->
        result.alongAxis(axis = axis, index = i).asMutable().setValue(array)
    }

    return result

}

fun <T> List<ArrayND<T>>.stack(axis: Int = 0): ArrayND<T>    = GenericArrayNDContext<T>().stack(this, axis)
fun List<DoubleArrayND> .stack(axis: Int = 0): DoubleArrayND = DoubleArrayNDContext.stack(this, axis)
fun List<FloatArrayND>  .stack(axis: Int = 0): FloatArrayND  = FloatArrayNDContext.stack(this, axis)
fun List<IntArrayND>    .stack(axis: Int = 0): IntArrayND    = IntArrayNDContext.stack(this, axis)
fun List<ByteArrayND>   .stack(axis: Int = 0): ByteArrayND   = ByteArrayNDContext.stack(this, axis)
