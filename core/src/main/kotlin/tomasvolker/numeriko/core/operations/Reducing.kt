package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND

inline fun reduce(
        indices: IntProgression,
        initial: Double,
        combine: (acc: Double, next: Double)->Double,
        selector: (Int)->Double
): Double {
    var result = initial
    for (i in indices) {
        result = combine(result, selector(i))
    }
    return result
}

inline fun DoubleArray1D.reduce(
        initial: Double,
        combine: (acc: Double, next: Double)->Double
): Double = reduce(indices, initial, combine) { i -> this[i] }

inline fun DoubleArray2D.reduce(
        initial: Double,
        axis: Int = 0,
        combine: (acc: Double, next: Double)->Double
): DoubleArray1D = when(axis) {
    0 -> doubleArray1D(shape1) { i1 ->
        reduce(indices1, initial, combine) { i0 -> this[i0, i1] }
    }
    1 -> doubleArray1D(shape0) { i0 ->
        reduce(indices0, initial, combine) { i1 -> this[i0, i1] }
    }
    else -> throw IllegalArgumentException()
}

inline fun DoubleArrayND.reduce(
        initial: Double,
        axis: Int = 0,
        combine: (acc: Double, next: Double)->Double
): DoubleArrayND {

    val resultShape = shape[0 until axis] concatenate shape[axis+1 until rank]

    return doubleArrayND(resultShape) { indices ->
        reduce(this.indices(axis), initial, combine) { ia ->
            val originIndex = indices[0 until axis] concatenate ia concatenate indices[axis until Last]
            this[originIndex]
        }
    }
}