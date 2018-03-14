package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArray
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArrayCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyNDArrayLinearCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.get
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface ReadOnlyArray1D<out T>: Collection<T> {

    val shape: ReadOnlyIntArray1D

    val indexShape: ReadOnlyIntArray1D get() = shape.shape

    val rank: Int get() = 1

    override val size: Int

    override fun isEmpty(): Boolean = size == 0

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

    fun getValue(i0: Int): T

    fun getValue(i0: Index): T

    fun getView(i0: IntProgression): ReadOnlyArray1D<T>

    fun getView(i0: IndexProgression): ReadOnlyArray1D<T>

    fun copy(): ReadOnlyArray1D<T>

    fun getDataAsArray(): Array<out T>

    fun unsafeGetDataAsArray(): Array<out T>

    fun getShapeAsArray(): IntArray

    fun unsafeGetShapeAsArray(): IntArray

    fun lastIndex() = size - 1

    override fun iterator(): ReadOnlyNDArrayLinearCursor<T> = linearCursor()

    fun linearCursor(): ReadOnlyNDArrayLinearCursor<T>

    fun cursor(): ReadOnlyNDArrayCursor<T>

}

fun <T> ReadOnlyArray1D<T>.defaultToString(): String =
        buildString { appendArray(this@defaultToString) }


fun <T> StringBuilder.appendArray(array: ReadOnlyArray1D<T>) {

        var first = true

        append("[ ")

        for (x in 0 until array.size) {

            if (first)
                first = false
            else
                append(", ")

            append(array.getValue(x))

        }

        append("] ")

}