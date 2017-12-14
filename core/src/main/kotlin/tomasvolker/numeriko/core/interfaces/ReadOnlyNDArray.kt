package tomasvolker.numeriko.core.interfaces

import tomasvolker.numeriko.core.array.index.All
import tomasvolker.numeriko.core.interfaces.integer.ReadOnlyIntNDArray

interface ReadOnlyNDArray<out T>: Iterable<T> {

    val shape: ReadOnlyIntNDArray

    val indexShape: ReadOnlyIntNDArray get() = shape.shape

    val dimension: Int
        get() = shape.size

    val size: Int
        get() = shape.reduce { acc, value ->  acc * value }

    fun getValue(vararg indices:Int): T

    fun getValue(indexArray: ReadOnlyIntNDArray): T

    fun getReadOnlyView(vararg indices:Any): ReadOnlyNDArray<T>

    fun copy(): ReadOnlyNDArray<T>

    fun dataAsArray(): Array<out T>

    fun shapeAsArray(): IntArray

    fun lastIndex(dimension: Int) = shape[dimension] - 1

    override fun iterator(): ReadOnlyNDArrayLinearCursor<out T> = linearCursor()

    fun linearCursor(): ReadOnlyNDArrayLinearCursor<out T>

    fun cursor(): ReadOnlyNDArrayCursor<out T>

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

        var first = true

        if (dimension == 1) {

            builder.append(this.joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = "] ",
                    limit = 100,
                    truncated = "... "
            ))

        } else {

            //TODO row iterator

            builder.append("[ ")

            for (x in 0 until this.shape[0]) {

                if (!first) {
                    builder.append(", ")
                    first = false
                }

                val indeces = Array<Any>(this.shape.size) { i ->
                    if (i == 0) x else All
                }

                builder.append(this.getReadOnlyView(*indeces))
            }

            builder.append("] ")

        }

        return builder.toString()
    }

}
