package tomasvolker.numeriko.core.operations.reduction

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.slicing.get

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
        reduce(indices0, initial, combine) { i0 -> this[i0, i1] }
    }
    1 -> doubleArray1D(shape0) { i0 ->
        reduce(indices1, initial, combine) { i1 -> this[i0, i1] }
    }
    else -> throw IllegalArgumentException()
}

inline fun DoubleArray2D.reduce(
        axis: Int = 0,
        reduction: (array: DoubleArray1D)->Double
): DoubleArray1D = when(axis) {
    0 -> doubleArray1D(shape1) { i1 ->
        reduction(this[All, i1])
    }
    1 -> doubleArray1D(shape0) { i0 ->
        reduction(this[i0, All])
    }
    else -> throw IllegalArgumentException()
}

inline fun DoubleArrayND.reduce(
        initial: Double,
        axis: Int = 0,
        combine: (acc: Double, next: Double)->Double
): DoubleArrayND =
        doubleArrayND(shape.remove(axis)) { indices ->
            reduce(this.indices(axis), initial, combine) { ir ->
                this[indices.inject(index = axis, value = ir)]
            }
        }

inline fun DoubleArrayND.reduce(
        axis: Int = 0,
        reduction: (acc: DoubleArray1D)->Double
): DoubleArrayND =
        doubleArrayND(shape.remove(axis)) { indices ->
            reduction(
                    this.get(
                        *Array<Any>(rank) { a ->
                            when {
                                a < axis -> indices[a]
                                a == axis -> All
                                else -> indices[a-1]
                            }
                        }
                    ).as1D()
            )
        }


