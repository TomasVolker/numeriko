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
     *
     */
    override fun contains(element:@UnsafeVariance T): Boolean {

        for (item in this) {

            if (item == element)
                return true

        }
        return false
    }

    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {

        for (element in elements) {

            if(!contains(element))
                return false

        }

        return true
    }

    fun getValue(vararg indices:Int): T

    fun getValue(indexArray: ReadOnlyIntNDArray): T

    fun getView(vararg indices:Any): ReadOnlyNDArray<T>

    fun copy(): ReadOnlyNDArray<T>

    fun dataAsArray(): Array<out T>

    fun shapeAsArray(): IntArray

    fun lastIndex(dimension: Int) = shape[dimension] - 1

    override fun iterator(): ReadOnlyNDArrayLinearCursor<T> = linearCursor()

    fun linearCursor(): ReadOnlyNDArrayLinearCursor<T>

    fun cursor(): ReadOnlyNDArrayCursor<T>

    fun defaultEquals(other: Any?): Boolean {

        when(other) {
            is NDArray<*> -> {

                if (other.shape != this.shape) {
                    return false
                }

                val thisIterator = iterator()
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

    fun defaultHashCode(): Int {

        val shapeHash = shape.reduce { acc, value->  31 * acc + value.hashCode()}

        var dataHash = 0

        forEach {
            dataHash = 31 * dataHash + (it?.hashCode() ?: 0)
        }

        return 31 * shapeHash + dataHash
    }

    fun defaultToString(newLineDimension: Int = 1): String {

        val builder = StringBuilder()
        
        if (rank == 1) {

            builder.append(this.joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = "] ",
                    limit = 100,
                    truncated = "... "
            ))

        } else {

            //TODO row iterator

            var first = true

            builder.append("[ ")

            for (x in 0 until this.shape[0]) {

                if (!first) {
                    builder.append(", ")
                }

                builder.append(this.getView(x))

                first = false
            }

            builder.append("] ")

        }

        return builder.toString()
    }

}
