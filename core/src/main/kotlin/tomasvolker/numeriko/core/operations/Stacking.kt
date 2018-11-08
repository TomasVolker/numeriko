package tomasvolker.numeriko.core.operations

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.asMutable
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.shape
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.preconditions.requireSameSize

infix fun DoubleArray1D.concatenate(other: DoubleArray1D): DoubleArray1D =
        doubleArray1D(this.size + other.size) { i ->
            if (i < this.size)
                this[i]
            else
                other[this.size + i]
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

fun DoubleArray2D.stack(other: DoubleArray1D, index: Int = 0): DoubleArray2D =
        when(index) {
            0 -> stack0(other)
            1 -> stack1(other)
            else -> throw IndexOutOfBoundsException(index)
        }

fun DoubleArray2D.stack0(other: DoubleArray1D): DoubleArray2D {

    require(this.shape1 == other.size)

    return doubleArray2D(shape0+1, shape1) { i0, i1 ->
        if (i0 < shape0)
            this[i0, i1]
        else
            other[i1]
    }

}

fun DoubleArray2D.stack1(other: DoubleArray1D): DoubleArray2D {

    require(this.shape0 == other.size)

    return doubleArray2D(shape0, shape1+1) { i0, i1 ->
        if (i1 < shape1)
            this[i0, i1]
        else
            other[i1]
    }

}


fun DoubleArray2D.stack(other: DoubleArray2D, index: Int = 0): DoubleArray2D =
        when(index) {
            0 -> stack0(other)
            1 -> stack1(other)
            else -> throw IndexOutOfBoundsException(index)
        }

fun DoubleArray2D.stack0(other: DoubleArray2D): DoubleArray2D {

    require(this.shape1 == other.size)

    return doubleArray2D(shape0+1, shape1) { i0, i1 ->
        if (i0 < shape0)
            this[i0, i1]
        else
            other[i0-shape0, i1]
    }

}

fun DoubleArray2D.stack1(other: DoubleArray2D): DoubleArray2D {

    require(this.shape0 == other.size)

    return doubleArray2D(shape0, shape1+1) { i0, i1 ->
        if (i1 < shape1)
            this[i0, i1]
        else
            other[i0, i1-shape1]
    }

}
