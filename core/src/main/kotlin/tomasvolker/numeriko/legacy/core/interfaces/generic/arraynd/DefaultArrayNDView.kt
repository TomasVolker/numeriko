package tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd

import tomasvolker.numeriko.legacy.core.interfaces.factory.intZeros
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

open class DefaultReadOnlyArrayNDView<T> internal constructor(
        open val array: ReadOnlyArrayND<T>,
        offset: ReadOnlyIntArray1D,
        shape: ReadOnlyIntArray1D,
        stride: ReadOnlyIntArray1D
) : ReadOnlyArrayND<T> {

    init {

        //TODO prerequisites

        require(offset.size == array.rank) {
            ""
        }

        require(shape.size <= array.rank) {
            ""
        }

        require(stride.size == array.rank) {
            ""
        }

    }

    protected val offset: ReadOnlyIntArray1D = offset.copy()

    override val shape: ReadOnlyIntArray1D = shape.copy()

    protected val stride: ReadOnlyIntArray1D = stride.copy()

    protected fun getBackingArrayIndex(indexArray: ReadOnlyIntArray1D): ReadOnlyIntArray1D {

        require(indexArray.size == shape.size) {
            TODO("Error message")
        }

        val result = intZeros(array.rank)

        var viewIndex = 0

        for (backingIndex in 0 until array.rank) {
            result[backingIndex] = offset[backingIndex]

            if (stride[backingIndex] != 0) {

                val currentIndex = indexArray[viewIndex]
                val currentSize = shape[viewIndex]

                if (currentIndex !in -currentSize until currentSize) {
                    throw IndexOutOfBoundsException(currentIndex)
                }

                result[backingIndex] += stride[backingIndex] * ((currentIndex + currentSize) % currentSize)
                viewIndex++
            }
        }

        return result
    }

    override fun getValue(indexArray: ReadOnlyIntArray1D) =
            array.getValue(getBackingArrayIndex(indexArray))

    override fun toString() = defaultToString()

    override fun equals(other: Any?) = defaultEquals(other)

    override fun hashCode() = defaultHashCode()

}

open class DefaultArrayNDView<T> internal constructor(
        override val array: ArrayND<T>,
        offset: ReadOnlyIntArray1D,
        shape: ReadOnlyIntArray1D,
        stride: ReadOnlyIntArray1D
) : DefaultReadOnlyArrayNDView<T>(array, offset, shape, stride), ArrayND<T> {

    override fun setValue(value: T, indexArray: ReadOnlyIntArray1D) {
        array.setValue(value, getBackingArrayIndex(indexArray))
    }

}