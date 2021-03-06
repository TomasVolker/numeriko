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

fun stack(arrays: List<DoubleArray1D>, axis: Int = 0): DoubleArray2D {

    if (axis !in 0..1) throw IllegalArgumentException("Stacking axis must be 0 or 1")

    if (arrays.isEmpty()) return doubleArray2D(0, 0) { _, _-> 0.0 }

    val firstSize = arrays.first().size
    require(arrays.all { it.size == firstSize }) { "All sizes must be the same" }

    return when(axis) {
        0 -> doubleArray2D(arrays.size, firstSize) { i0, i1 ->
            arrays[i0][i1]
        }
        1 -> doubleArray2D(firstSize, arrays.size) { i0, i1 ->
            arrays[i1][i0]
        }
        else -> throw IllegalStateException()
    }

}

fun stack(
        vararg arrays: DoubleArray1D,
        axis: Int = 0
): DoubleArray2D = stack(arrays.toList(), axis)

infix fun DoubleArray1D.concatenate(other: DoubleArray1D): DoubleArray1D =
        doubleArray1D(this.size + other.size) { i ->
            if (i < this.size)
                this[i]
            else
                other[i - this.size]
        }

infix fun IntArray1D.concatenate(other: IntArray1D): IntArray1D =
        intArray1D(this.size + other.size) { i ->
            if (i < this.size)
                this[i]
            else
                other[i - this.size]
        }

infix fun IntArray1D.concatenate(other: Int): IntArray1D =
        intArray1D(this.size + 1) { i ->
            if (i < this.size)
                this[i]
            else
                other
        }

infix fun DoubleArray1D.stack(other: DoubleArray1D): DoubleArray2D {
    requireSameSize(this, other)

    return doubleArray2D(2, this.size) { i0, i1 ->
        when(i0) {
            0 -> this[i1]
            1 -> other[i1]
            else -> throw IllegalStateException()
        }
    }
}

fun DoubleArray2D.concatenate(other: DoubleArray1D, axis: Int = 0): DoubleArray2D =
        when(axis) {
            0 -> {
                require(this.shape1 == other.size)
                doubleArray2D(shape0+1, shape1) { i0, i1 ->
                    if (i0 < shape0)
                        this[i0, i1]
                    else
                        other[i1]
                }
            }
            1 -> {
                require(this.shape0 == other.size)
                doubleArray2D(shape0, shape1+1) { i0, i1 ->
                    if (i1 < shape1)
                        this[i0, i1]
                    else
                        other[i1]
                }
            }
            else -> throw IndexOutOfBoundsException("$axis")
        }


fun DoubleArray2D.concatenate(other: DoubleArray2D, axis: Int = 0): DoubleArray2D =
        when(axis) {
            0 -> {
                require(this.shape1 == other.size)
                doubleArray2D(shape0+1, shape1) { i0, i1 ->
                    if (i0 < shape0)
                        this[i0, i1]
                    else
                        other[i0 - shape0, i1]
                }
            }
            1 -> {
                require(this.shape0 == other.size)
                doubleArray2D(shape0, shape1+1) { i0, i1 ->
                    if (i1 < shape1)
                        this[i0, i1]
                    else
                        other[i0, i1 - shape1]
                }
            }
            else -> throw IndexOutOfBoundsException("$axis")
        }
