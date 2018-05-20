package tomasvolker.numeriko.legacy.core.interfaces.generic.array1d

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ReadOnlyArrayND
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

interface ReadOnlyArray1D<out T>: ReadOnlyArrayND<T> {

    override val size: Int

    override val shape: ReadOnlyIntArray1D get() =
        intArray1DOf(size)

    override val rank: Int get() = 1

    fun getValue(i0: Int): T

    fun getValue(i0: Index): T =
            getValue(i0.computeValue(size))

    override fun getValue(indexArray: ReadOnlyIntArray1D): T {
        require(indexArray.size == 1) {
            "Index array size ${indexArray.size} different from rank 1"
        }
        return getValue(indexArray.getValue(0))
    }

    fun getView(i0: IntProgression): ReadOnlyArray1D<T> =
            DefaultReadOnlyArray1DView(
                    array = this,
                    offset = i0.first,
                    size = i0.count(),
                    stride = i0.step
            )

    fun getView(i0: IndexProgression): ReadOnlyArray1D<T> =
            getView(i0.computeProgression(size))

    fun lastIndex() = size - 1

}
