package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.factory.array2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.preconditions.requireSameSize
import java.lang.IllegalArgumentException


inline fun <reified T> stack(vararg arrays: Array1D<T>): Array2D<T> {

    if (arrays.isEmpty()) return array2D<T>(0, 0) { _, _-> TODO() }

    val firstSize = arrays.first().size
    require(arrays.all { it.size == firstSize }) { "All sizes must be the same" }

    return array2D(arrays.size, firstSize) { i0, i1 ->
        arrays[i0].getValue(i1)
    }

}

fun List<DoubleArray1D>.stack(axis: Int = 0): DoubleArray2D {

    if (axis !in 0..1) throw IllegalArgumentException("Stacking axis must be 0 or 1")

    if (isEmpty()) return doubleArray2D(0, 0) { _, _-> 0.0 }

    val firstSize = first().size
    require(all { it.size == firstSize }) { "All sizes must be the same" }

    return when(axis) {
        0 -> doubleArray2D(size, firstSize) { i0, i1 ->
            this[i0][i1]
        }
        1 -> doubleArray2D(firstSize, size) { i0, i1 ->
            this[i1][i0]
        }
        else -> throw IllegalStateException()
    }

}

fun stack(
        vararg arrays: DoubleArray1D,
        axis: Int = 0
): DoubleArray2D = arrays.toList().stack(axis)
