package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.unsafeGetView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import kotlin.math.max
import kotlin.math.min

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

inline fun DoubleArray2D.reduce(
        axis: Int = 0,
        reduction: (array: DoubleArray1D)->Double
): DoubleArray1D =
        doubleArray1D(shape.remove(axis)[0]) { i ->
            reduction(this.arrayAlongAxis(axis = axis, index = i))
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
                    this.unsafeGetView(
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


fun IntArray1D.remove(index: Int): IntArray1D =
        intArray1D(size - 1) { i ->
            if (i < index)
                this[i]
            else
                this[i + 1]
        }

fun IntArray1D.inject(index: Int, value: Int): IntArray1D =
        intArray1D(size + 1) { i ->
            when {
                i < index -> this[i]
                i == index -> value
                i > index -> this[i - 1]
                else -> throw IllegalStateException()
            }
        }
