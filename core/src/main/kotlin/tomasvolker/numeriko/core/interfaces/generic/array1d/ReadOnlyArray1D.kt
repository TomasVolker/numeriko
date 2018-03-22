package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.core.interfaces.integer.array1d.ReadOnlyIntArray1D

interface ReadOnlyArray1D<out T>: ReadOnlyArrayND<T> {

    override val shape: ReadOnlyIntArray1D get() = intArray1DOf(size)

    override val indexShape: ReadOnlyIntArray1D get() = intArray1DOf(1)

    override val rank: Int get() = 1

    override val size: Int

    fun getValue(i0: Int): T

    fun getValue(i0: Index): T

    fun getView(i0: IntProgression): ReadOnlyArray1D<T>

    fun getView(i0: IndexProgression): ReadOnlyArray1D<T>

    override fun getView(vararg indices: Any): ReadOnlyArrayND<T> {
        require(indices.size <= 1) { "${indices.size} indices provided, expected 1 or less" }

        TODO("implement")
    }

    override fun copy(): ReadOnlyArray1D<T>

    fun lastIndex() = size - 1

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
