package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D


infix fun DoubleArray1D.concat(other: DoubleArray1D) = concatenate(other)

infix fun DoubleArray1D.concatenate(other: DoubleArray1D): DoubleArray1D =
        doubleArray1D(this.size + other.size) { i ->
            if (i < this.size)
                this[i]
            else
                other[i - this.size]
        }
infix fun IntArray1D.concat(other: IntArray1D) = concatenate(other)

infix fun IntArray1D.concatenate(other: IntArray1D): IntArray1D =
        intArray1D(this.size + other.size) { i ->
            if (i < this.size)
                this[i]
            else
                other[i - this.size]
        }

infix fun IntArray1D.concat(other: Int) = concatenate(other)

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
                        other[i0]
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
