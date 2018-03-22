package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D
import tomasvolker.numeriko.core.interfaces.integer.arraynd.ReadOnlyIntArrayND
import tomasvolker.numeriko.core.jvm.int.array1d.asArray1D
import tomasvolker.numeriko.core.util.computeSizeFromShape

interface ReadOnlyArrayNDViewer<out T> {

    val array: ReadOnlyArrayND<T>

    operator fun get(vararg indices: Any): ReadOnlyArrayND<T> = array.getView(*indices)

}

class DefaultReadOnlyArrayNDViewer<out T>(override val array: ReadOnlyArrayND<T>): ReadOnlyArrayNDViewer<T>

/**
 * Interface for generic read only N dimensional arrays
 *
 * Basic interface for a read-only N dimensional generic array. Note that read-only does not
 * imply immutability (the mutable ArrayND interface inherits from this one). This
 * follows Kotlin's Collection and MutableCollection design pattern.
 *
 * @param T the type of elements of this array
 * @see ArrayND
 */
interface ReadOnlyArrayND<out T>: Collection<T> {

    /**
     * Shape of the array
     *
     * Shape of the ArrayND as a rank one ReadOnlyArrayND. An empty shape indicates
     * an ArrayND of rank 0, which is equivalent to a scalar. The array contains the lengths
     * of the different dimensions, which are always non-negative (may be zero).
     */
    val shape: ReadOnlyIntArray1D

    val view: ReadOnlyArrayNDViewer<T> get() = DefaultReadOnlyArrayNDViewer(this)

    /**
     * Rank of the array.
     *
     * Rank of the ArrayND, which is the size of the shape. The rank may be zero, making the array equivalent to a
     * scalar.
     */
    val rank: Int
        get() = shape.size

    /**
     * Size of the array.
     *
     * Size of the ArrayND. Indicates the amount of elements it stores. It is the product
     * of the length in all dimensions. For a rank zero array the size is 1.
     */
    override val size: Int
        get() = computeSizeFromShape(shape)

    /**
     * Indicates if the array is empty.
     *
     * @return true if the array is empty, which is equivalent to having size zero.
     */
    override fun isEmpty(): Boolean = size == 0

    /**
     * Indicates if the array contains the given element.
     *
     * @return true if an element is equal to any element in the array according to [equals].
     */
    override fun contains(element:@UnsafeVariance T): Boolean {

        for (item in this) {

            if (item == element)
                return true

        }
        return false
    }

    /**
     * Indicates if the array contains all the given elements.
     *
     * @return true if all elements in [elements] are contained in the array according to [contains].
     */
    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {

        for (element in elements) {

            if(!contains(element))
                return false

        }

        return true
    }

    /**
     * Get the valued in the given indices.
     *
     * Getter function of the item on the given indices. If a [rank] amount of indices must be provided or
     * [IllegalArgumentException] will be thrown. This the generic getter, to avoid boxing on primitive types the
     * corresponding getter should be used (e.g. [ReadOnlyIntArrayND.getInt])
     *
     * @return the element in the given indices
     * @throws IllegalArgumentException when the amount of indices provided is not equal to [rank].
     */
    fun getValue(vararg indices: Int): T = getValue(indices.asArray1D())

    /**
     * Get the valued in the given indices.
     *
     * Getter function of the item on the given indices. If the indexArray has not the shape of [indexShape] an
     * [IllegalArgumentException] will be thrown. This the generic getter, to about boxing on primitive types the
     * corresponding getter should be used (e.g. [ReadOnlyIntArrayND.getInt])
     *
     * @return the element in the given indices
     * @throws IllegalArgumentException when the shape of [indexArray] is not equal to [indexShape].
     */
    fun getValue(indexArray: ReadOnlyIntArray1D): T

    /**
     * Get a view of the given indeces and index ranges.
     *
     * Getter function for a view on the given indices. The indices array must contain objects of class Int, IntRange,
     * Index or IndexProgression, or an [IllegalArgumentException] will be thrown. Both this array and the view
     * returned by this function work over the same backing array.
     *
     * @return a [ReadOnlyArrayND] which is a view of this array.
     * @throws IllegalArgumentException if an object which is not a Int, IntRange, Index or IndexProgression is
     * passed.
     */
    fun getView(vararg indices: Any): ReadOnlyArrayND<T>

    /**
     * Copies the array.
     *
     * @return a copy of this array
     */
    fun copy(): ReadOnlyArrayND<T>

    /**
     * Computes the last index on the given dimension. If the size of the dimension is zero, this returns -1.
     *
     * @return the last valid index on the given dimension.
     */
    fun lastIndex(dimension: Int) = shape[dimension] - 1

    /**
     * Returns an iterator which traverses the array in row mayor order
     *
     */
    override fun iterator(): ReadOnlyArrayNDIterator<T> = cursor()

    /**
     * Returns a cursor providing functionality to move on different dimensions.
     */
    fun cursor(): ReadOnlyArrayNDCursor<T> = DefaultReadOnlyArrayNDCursor(this)

}

fun <T> ReadOnlyArrayND<T>.defaultEquals(other: Any?): Boolean {

    when(other) {
        is ArrayND<*> -> {

            if (this.rank != other.rank)
                return false

            if (rank == 0)
                return this.getValue() == other.getValue()

            if (rank == 1) {
                if (this.shape.getInt(0) != other.shape.getInt(0))
                    return false
            } else if (this.shape != other.shape) {
                return false
            }

            val thisIterator = this.iterator()
            val otherIterator = other.iterator()

            while (thisIterator.hasNext()) {
                if (thisIterator.next() != otherIterator.next())
                    return false
            }

            return true
        }
        else -> return false
    }

}

fun <T> ReadOnlyArrayND<T>.defaultHashCode(): Int {

    val shapeHash = shape.reduce { acc, value->  31 * acc + value.hashCode()}

    var dataHash = 0

    forEach {
        dataHash = 31 * dataHash + (it?.hashCode() ?: 0)
    }

    return 31 * shapeHash + dataHash
}

fun <T> ReadOnlyArrayND<T>.defaultToString(): String =
        buildString { appendArray(this@defaultToString) }


fun <T> StringBuilder.appendArray(array: ReadOnlyArrayND<T>) {

    when (array.rank) {

        0 -> {
            append(array.get())
        }

        else -> {

            //TODO row iterator

            var first = true

            append("[ ")

            for (x in 0 until array.shape[0]) {

                if (first)
                    first = false
                else
                    append(", ")

                appendArray(array.getView(x))

            }

            append("] ")

        }
    }

}