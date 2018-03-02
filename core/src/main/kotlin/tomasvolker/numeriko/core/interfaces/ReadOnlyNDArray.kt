package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray
import tomasvolker.numeriko.core.util.computeSizeFromShape

/**
 * Basic interface for a read-only N dimensional generic array. Note that read-only does not
 * imply immutability (in fact the mutable NDArray interface inherits from this one). This
 * follows Kotlin's Collection and MutableCollection design pattern.
 */
interface ReadOnlyNDArray<out T>: Collection<T> {

    /**
     * Shape of the NDArray as a one dimensional ReadOnlyNDArray. An empty shape indicates
     * an NDArray of rank 0, which is equivalent to a scalar. The array contains the lengths
     * of the diferent dimensions, which are always non-negative (may be zero).
     */
    val shape: ReadOnlyIntNDArray

    /**
     * Shape of an index NDArray. This is equal to the shape of the shape.
     */
    val indexShape: ReadOnlyIntNDArray get() = shape.shape

    /**
     * Rank of the NDArray, which is the size of the shape.
     */
    val rank: Int
        get() = shape.size

    /**
     * Size of the NDArray. Indicates the amount of elements it stores. It is the product
     * of the length in all dimensions. For a rank 0 array the size is 1.
     */
    override val size: Int
        get() = computeSizeFromShape(shapeAsArray())

    /**
     * Indicates if the array is empty, in other words, if the size is zero.
     */
    override fun isEmpty(): Boolean = size == 0

    /**
     * Indicates if the array contains the given element.
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
     */
    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {

        for (element in elements) {

            if(!contains(element))
                return false

        }

        return true
    }

    /**
     * Getter function of the item on the given indices. If there is not the right amount of indices an
     * IllegalArgumentException will be thrown. This the generic getter, to about boxing on primitive types the
     * corresponding getter should be used (e.g. getInt)
     */
    fun getValue(vararg indices:Int): T

    /**
     * Getter function of the item on the given indices. If the indexArray has not the right shape an
     * IllegalArgumentException will be thrown. This the generic getter, to about boxing on primitive types the
     * corresponding getter should be used (e.g. getInt)
     */
    fun getValue(indexArray: ReadOnlyIntNDArray): T

    /**
     * Getter function for a view on the given indices. The indices array must contain objects of class Int, IntRange,
     * AbstractIndex or IndexProgression.
     */
    fun getView(vararg indices:Any): ReadOnlyNDArray<T>

    /**
     * Copies the array using the same implementation.
     */
    fun copy(): ReadOnlyNDArray<T>

    /**
     * Returns the data as a JVM array on row mayor order. The array return will be the backing array when possible so
     * it should only be read, not written.
     */
    fun dataAsArray(): Array<out T>

    /**
     * Returns the shape as a JVM array. The array return will be the backing array when possible so
     * it should only be read, not written.
     */
    fun shapeAsArray(): IntArray

    /**
     * Computes the last index on the given dimension.
     */
    fun lastIndex(dimension: Int) = shape[dimension] - 1

    /**
     * Returns an iterator which traverses the array in row mayor order, also providing functionality to move on
     * different dimensions.
     */
    override fun iterator(): ReadOnlyNDArrayLinearCursor<T> = linearCursor()

    /**
     * Returns a linear iterator which traverses the array in row mayor order.
     */
    fun linearCursor(): ReadOnlyNDArrayLinearCursor<T>

    /**
     * Returns a cursor which traverses the array in row mayor order, also providing functionality to move on
     * different dimensions.
     */
    fun cursor(): ReadOnlyNDArrayCursor<T>

    operator fun component1(): T = getValue(0)

    operator fun component2(): T = getValue(1)

    operator fun component3(): T = getValue(2)

    operator fun component4(): T = getValue(3)

    operator fun component5(): T = getValue(4)

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

fun <T> ReadOnlyNDArray<T>.defaultToString(newLineDimension: Int = 1): String =
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

                if (!first) append(", ")

                appendArray(array.getView(x))

                first = false
            }

            append("] ")

        }
    }

}