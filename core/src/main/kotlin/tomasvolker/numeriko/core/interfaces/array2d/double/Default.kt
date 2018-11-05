package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.preconditions.requireSameShape

fun defaultEquals(array1: DoubleArray2D, array2: DoubleArray2D): Boolean {

    requireSameShape(array1, array2)

    array1.forEachIndex { i0, i1 ->
        if (array1.getDouble(i0, i1) != array2.getDouble(i0, i1))
            return false
    }

    return true
}

fun defaultHashCode(array1: DoubleArray2D): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + x.hashCode()
    }

    return result
}


class DefaultDoubleArray2DIterator(
        val array: DoubleArray2D
): DoubleIterator() {

    var i0 = 0
    var i1 = 0

    override fun hasNext(): Boolean =
            i0 < array.shape0 && i1 < array.shape1

    override fun nextDouble(): Double {
        val result = array.getDouble(i0, i1)
        i1++
        if (i1 == array.shape1) {
            i1 = 0
            i0++
        }
        return result
    }

}

open class DefaultDoubleArray2DView(
        open val array: DoubleArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DoubleArray2D {

    override fun getDouble(i0: Int, i1: Int): Double {
        checkIndices(i0, i1)

        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleArray2D) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

open class DefaultMutableDoubleArray2DView(
        open val array: MutableDoubleArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : MutableDoubleArray2D {

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        checkIndices(i0, i1)

        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun getDouble(i0: Int, i1: Int): Double {
        checkIndices(i0, i1)

        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array2D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}


class DoubleArray2D1DView(
        val array: DoubleArray2D
) : DoubleArray1D {

    val dim: Int

    init {

        dim = when {
            array.shape0 == 1 -> 1
            array.shape1 == 1 -> 0
            else -> throw IllegalArgumentException("array is not flat")
        }

    }

    override val size: Int get() = array.size

    override fun getDouble(index: Int): Double {

        return when {
            dim == 0 -> array.getValue(
                    index,
                    0
            )
            dim == 1 -> array.getValue(
                    0,
                    index
            )
            else -> throw IllegalStateException()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Array1D<*>) return false
        return defaultEquals(this, other)
    }

    override fun hashCode(): Int = defaultHashCode(this)

    override fun toString(): String = defaultToString(this)

}

