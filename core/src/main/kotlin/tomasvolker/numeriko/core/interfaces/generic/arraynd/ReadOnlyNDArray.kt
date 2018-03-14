package tomasvolker.numeriko.core.interfaces.generic.arraynd

import tomasvolker.numeriko.core.interfaces.int.arraynd.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

interface ReadOnlyNDArrayViewer<out T> {

    val array: ReadOnlyNDArray<T>

    operator fun get(vararg indices: Any): ReadOnlyNDArray<T> = array.getView(*indices)

}

class DefaultReadOnlyNDArrayViewer<out T>(override val array: ReadOnlyNDArray<T>): ReadOnlyNDArrayViewer<T>

/**
 * Interface for generic read only N dimensional arrays
 *
 * Basic interface for a read-only N dimensional generic jvm. Note that read-only does not
 * imply immutability (the mutable NDArray interface inherits from this one). This
 * follows Kotlin's Collection and MutableCollection design pattern.
 *
 * @param T the type of elements of this jvm
 * @see NDArray
 */
interface ReadOnlyNDArray<out T>: Collection<T> {

    /**
     * Shape of the jvm
     *
     * Shape of the NDArray as a rank one ReadOnlyNDArray. An empty shape indicates
     * an NDArray of rank 0, which is equivalent to a scalar. The jvm contains the lengths
     * of the different dimensions, which are always non-negative (may be zero).
     */
    val shape: ReadOnlyIntNDArray

    /**
     * Shape of an index of the jvm
     *
     * Shape of an index for this NDArray. This is equal to the shape of the shape. This is a rank one jvm with one
     * element containing the amount of indices of a valid index.
     */
    val indexShape: ReadOnlyIntNDArray get() = shape.shape

    val view: ReadOnlyNDArrayViewer<T> get() = DefaultReadOnlyNDArrayViewer(this)

    /**
     * Rank of the jvm.
     *
     * Rank of the NDArray, which is the size of the shape. The rank may be zero, making the jvm equivalent to a
     * scalar.
     */
    val rank: Int
        get() = shape.size

    /**
     * Size of the jvm.
     *
     * Size of the NDArray. Indicates the amount of elements it stores. It is the product
     * of the length in all dimensions. For a rank zero jvm the size is 1.
     */
    override val size: Int
        get() = computeSizeFromShape(unsafeGetShapeAsArray())

    /**
     * Indicates if the jvm is empty.
     *
     * @return true if the jvm is empty, which is equivalent to having size zero.
     */
    override fun isEmpty(): Boolean = size == 0

    /**
     * Indicates if the jvm contains the given element.
     *
     * @return true if an element is equal to any element in the jvm according to [equals].
     */
    override fun contains(element:@UnsafeVariance T): Boolean {

        for (item in this) {

            if (item == element)
                return true

        }
        return false
    }

    /**
     * Indicates if the jvm contains all the given elements.
     *
     * @return true if all elements in [elements] are contained in the jvm according to [contains].
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
     * corresponding getter should be used (e.g. [ReadOnlyIntNDArray.getInt])
     *
     * @return the element in the given indices
     * @throws IllegalArgumentException when the amount of indices provided is not equal to [rank].
     */
    fun getValue(vararg indices:Int): T

    /**
     * Get the valued in the given indices.
     *
     * Getter function of the item on the given indices. If the indexArray has not the shape of [indexShape] an
     * [IllegalArgumentException] will be thrown. This the generic getter, to about boxing on primitive types the
     * corresponding getter should be used (e.g. [ReadOnlyIntNDArray.getInt])
     *
     * @return the element in the given indices
     * @throws IllegalArgumentException when the shape of [indexArray] is not equal to [indexShape].
     */
    fun getValue(indexArray: ReadOnlyIntNDArray): T

    /**
     * Get a view of the given indeces and index ranges.
     *
     * Getter function for a view on the given indices. The indices jvm must contain objects of class Int, IntRange,
     * Index or IndexProgression, or an [IllegalArgumentException] will be thrown. Both this jvm and the view
     * returned by this function work over the same backing jvm.
     *
     * @return a [ReadOnlyNDArray] which is a view of this jvm.
     * @throws IllegalArgumentException if an object which is not a Int, IntRange, Index or IndexProgression is
     * passed.
     */
    fun getView(vararg indices:Any): ReadOnlyNDArray<T>

    /**
     * Copies the jvm.
     *
     * @return a copy of this jvm
     */
    fun copy(): ReadOnlyNDArray<T>

    /**
     * Get a copy of the data as an [Array] in row mayor order.
     *
     * Returns a copy of the data as a JVM jvm on row mayor order. To access the backing JVM jvm if the
     * implementation uses one use [unsafeGetDataAsArray]
     *
     * @return a copy of the data as a JVM jvm on row mayor order.
     */
    fun getDataAsArray(): Array<out T>

    /**
     * Get the backing data as an [Array] if possible or a copy, in row mayor order.
     *
     * Returns the backing JVM jvm data in row mayor order if the implementation uses one, or a copy of not. This
     * method should not be used in general, only when there is a significant performance advantage. The returned jvm
     * MUST only be read from, not written.
     *
     * @return the backing data JVM jvm if the implementation uses one or a copy containing the elements in row mayor order.
     */
    fun unsafeGetDataAsArray(): Array<out T>

    /**
     * Get a copy of the shape as an [Array].
     *
     * Returns a copy of the shape as a JVM jvm. To access the backing JVM jvm if the
     * implementation uses one use [unsafeGetShapeAsArray]
     *
     * @return a copy of the shape as a JVM jvm on row mayor order.
     */
    fun getShapeAsArray(): IntArray

    /**
     * Get the backing shape as an [Array] if possible or a copy.
     *
     * Returns the backing JVM jvm shape if the implementation uses one, or a copy of not. This method should not be
     * used in general, only when there is a significant performance advantage. The returned jvm MUST only be read
     * from, not written.
     *
     * @return the backing shape JVM jvm if the implementation uses one or a copy.
     */
    fun unsafeGetShapeAsArray(): IntArray

    /**
     * Computes the last index on the given dimension. If the size of the dimension is zero, this returns -1.
     *
     * @return the last valid index on the given dimension.
     */
    fun lastIndex(dimension: Int) = shape[dimension] - 1

    /**
     * Returns an iterator which traverses the jvm in row mayor order, also providing functionality to move on
     * different dimensions.
     *
     */
    override fun iterator(): ReadOnlyNDArrayLinearCursor<T> = linearCursor()

    /**
     * Returns a linear iterator which traverses the jvm in row mayor order.
     */
    fun linearCursor(): ReadOnlyNDArrayLinearCursor<T>

    /**
     * Returns a cursor which traverses the jvm in row mayor order, also providing functionality to move on
     * different dimensions.
     */
    fun cursor(): ReadOnlyNDArrayCursor<T>

}

fun <T> ReadOnlyNDArray<T>.defaultEquals(other: Any?): Boolean {

    when(other) {
        is NDArray<*> -> {

            if (other.rank == 1 && this.rank == 1) {

                if (other.shape[0] != this.shape[0]) {
                    return false
                }

            } else if (other.shape != this.shape) {
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

fun <T> ReadOnlyNDArray<T>.defaultHashCode(): Int {

    val shapeHash = shape.reduce { acc, value->  31 * acc + value.hashCode()}

    var dataHash = 0

    forEach {
        dataHash = 31 * dataHash + (it?.hashCode() ?: 0)
    }

    return 31 * shapeHash + dataHash
}

fun <T> ReadOnlyNDArray<T>.defaultToString(): String =
        buildString { appendArray(this@defaultToString) }


fun <T> StringBuilder.appendArray(array: ReadOnlyNDArray<T>) {

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