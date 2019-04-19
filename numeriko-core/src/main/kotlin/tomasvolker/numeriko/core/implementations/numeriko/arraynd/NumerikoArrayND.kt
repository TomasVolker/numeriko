package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.numeriko.NumerikoArray
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class NumerikoArrayND<T>(
        override val shape: IntArray1D,
        val data: Array<T>,
        val offset: Int = 0,
        val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableArrayND<T>(), NumerikoArray<T> {

    override val backingArray: Array<T>
        get() = data

    override val rank: Int
        get() = shape.size

    private val arrayShape = shape.toIntArray()

    val fullData: Boolean = shape.product() == data.size

    override fun shape(axis: Int): Int = arrayShape[axis]

    override val size: Int get() = shape.product()

    override fun getSlice(slice: ArraySlice): NumerikoArrayND<T> =
            NumerikoArrayND(
                    shape = slice.shape.toIntArray1D(),
                    data = data,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[slice.permutation[a]]
                    }
            )

    override fun getValue(): T {
        requireRank(0)
        return data[offset]
    }

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return data[convertIndices(i0)]
    }

    override fun getValue(i0: Int, i1: Int): T  {
        requireValidIndices(i0, i1)
        return data[convertIndices(i0, i1)]
    }

    override fun getValue(i0: Int, i1: Int, i2: Int): T {
        requireValidIndices(i0, i1, i2)
        return data[convertIndices(i0, i1, i2)]
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int): T {
        requireValidIndices(i0, i1, i2, i3)
        return data[convertIndices(i0, i1, i2, i3)]
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): T {
        requireValidIndices(i0, i1, i2, i3, i4)
        return data[convertIndices(i0, i1, i2, i3, i4)]
    }

    override fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): T {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return data[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun setValue(i0: Int, value: T) {
        requireValidIndices(i0)
        data[convertIndices(i0)] = value
    }

    override fun setValue(i0: Int, i1: Int, value: T) {
        requireValidIndices(i0, i1)
        data[convertIndices(i0, i1)] = value
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, value: T) {
        requireValidIndices(i0, i1, i2)
        data[convertIndices(i0, i1, i2)] = value
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3)
        data[convertIndices(i0, i1, i2, i3)] = value
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3, i4)
        data[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override fun setValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: T) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        data[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

    private fun convertIndices(i0: Int): Int =
            linearIndex(offset, strideArray, i0)

    private fun convertIndices(i0: Int, i1: Int): Int =
            linearIndex(offset, strideArray, i0, i1)

    private fun convertIndices(i0: Int, i1: Int, i2: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4, i5)

    override fun setValue(value: ArrayND<T>) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is NumerikoArray<*> && value.backingArray !== this.data)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setValue(indices, element)
        }

    }

    override fun copy(): ArrayND<T> =
            if (fullData)
                NumerikoArrayND(
                        shape = shape.copy(),
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super.copy()

}

