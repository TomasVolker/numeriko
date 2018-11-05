package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.preconditions.requireSameShape

fun defaultEquals(array1: Array2D<*>, array2: Array2D<*>): Boolean {

    requireSameShape(array1, array2)

    array1.forEachIndex { i0, i1 ->
        if (array1.getValue(i0, i1) != array2.getValue(i0, i1))
            return false
    }

    return true
}

fun defaultHashCode(array1: Array2D<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun defaultToString(array: Array2D<*>) = buildString {

    append("[")

    for (i0 in array.indices(0)) {

        if (i0 > 0) {
            append(",\n ")
        }

        append(
                array.getView(i0, All).joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]",
                    limit = 20,
                    truncated = "..."
            )
        )

    }

    append("]")

}




class DefaultArray2DIterator<T>(
        val array: Array2D<T>
): Iterator<T> {

    var i0 = 0
    var i1 = 0

    override fun hasNext(): Boolean =
            i0 < array.shape0 && i1 < array.shape1

    override fun next(): T {
        val result = array.getValue(i0, i1)
        i1++
        if (i1 == array.shape1) {
            i1 = 0
            i0++
        }
        return result
    }

}

open class DefaultArray2DView<out T>(
        open val array: Array2D<T>,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : Array2D<T> {

    override fun getValue(i0: Int, i1: Int): T {
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

open class DefaultMutableArray2DView<T>(
        open val array: MutableArray2D<T>,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : MutableArray2D<T> {

    override fun setValue(value: T, i0: Int, i1: Int) {
        checkIndices(i0, i1)

        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun getValue(i0: Int, i1: Int): T {
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


class Array2D1DView<out T>(
        val array: Array2D<T>
) : Array1D<T> {

    val dim: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun getValue(index: Int): T {

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