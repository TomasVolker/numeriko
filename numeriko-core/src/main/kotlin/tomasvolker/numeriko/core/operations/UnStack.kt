package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Size
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.performance.forEach
import java.lang.IllegalArgumentException


@JvmName("stackN")
fun List<DoubleArrayND>.stack(axis: Int = 0): DoubleArrayND {

    if (isEmpty())
        return doubleZeros(I[0])

    if (all { it is DoubleArray0D })
        return doubleArray1D(size) { i -> this[i].as0D().get() }

    if (all { it is DoubleArray1D })
        return (this as List<DoubleArray1D>).stack(axis)

    if (all { it is DoubleArray2D })
        return (this as List<DoubleArray2D>).stack(axis)

    val firstShape = first().shape
    require(all { it.shape == firstShape }) { "All shapes must be the same" }

    val result = doubleZeros(
            firstShape[0 until axis] concat size concat firstShape[axis until Size]
    ).asMutable()

    forEachIndexed { i, array ->
        result.arrayAlongAxis(axis = axis, index = i).setValue(array)
    }

    return result
}

@JvmName("stack2")
fun List<DoubleArray2D>.stack(axis: Int = 0): DoubleArrayND {
    val arrayList = this

    require(axis in 0..2) { "Stacking axis must be 0, 1 or 2" }

    if (isEmpty()) return doubleArrayND(I[0, 0, 0]) { 0.0 }

    val resultShape = first().shape
    require(all { it.shape == resultShape }) { "All shapes must be the same" }

    return when(axis) {
        0 -> doubleZeros(I[size, resultShape[0], resultShape[1]]).asMutable().apply {
            forEach(arrayList.size, resultShape[0], resultShape[1]) { i0, i1, i2 ->
                this[i0, i1, i2] = arrayList[i0][i1, i2]
            }
        }
        1 -> doubleZeros(I[resultShape[0], arrayList.size, resultShape[1]]).asMutable().apply {
            forEach(resultShape[0], arrayList.size, resultShape[1]) { i0, i1, i2 ->
                this[i0, i1, i2] = arrayList[i1][i0, i2]
            }
        }
        2 -> doubleZeros(I[resultShape[0], resultShape[1], arrayList.size]).asMutable().apply {
            forEach(resultShape[0], resultShape[1], arrayList.size) { i0, i1, i2 ->
                this[i0, i1, i2] = arrayList[i2][i0, i1]
            }
        }
        else -> throw IllegalStateException()
    }

}


fun DoubleArray2D.unstack(axis: Int = 0): List<DoubleArray1D> =
        List(shape(axis)) { i -> this.arrayAlongAxis(axis = axis, index = i) }

fun DoubleArrayND.unstack(axis: Int = 0): List<DoubleArrayND> =
        List(shape(axis)) { i -> this.arrayAlongAxis(axis = axis, index = i) }
