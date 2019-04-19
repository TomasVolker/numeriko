package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.numeriko.NumerikoIntArray
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.DefaultMutableIntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class NumerikoIntArrayND(
        override val shape: IntArray1D,
        val data: IntArray,
        val offset: Int = 0,
        val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableIntArrayND(), NumerikoIntArray {

    override val backingArray: IntArray
        get() = data

    override val rank: Int
        get() = shape.size

    val fullData: Boolean = size == data.size

    override fun getSlice(slice: ArraySlice): NumerikoIntArrayND =
            NumerikoIntArrayND(
                    shape = slice.shape.toIntArray1D(),
                    data = data,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[slice.permutation[a]]
                    }
            )

    override fun shape(axis: Int): Int = shape[axis]

    override val size: Int get() = shape.product()

    override fun get(): Int {
        requireRank(0)
        return data[offset]
    }

    override operator fun get(i0: Int): Int {
        requireValidIndices(i0)
        return data[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Int  {
        requireValidIndices(i0, i1)
        return data[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Int {
        requireValidIndices(i0, i1, i2)
        return data[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Int {
        requireValidIndices(i0, i1, i2, i3)
        return data[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4)
        return data[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return data[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun set(value: Int) {
        requireRank(0)
        data[offset] = value
    }

    override operator fun set(i0: Int, value: Int) {
        requireValidIndices(i0)
        data[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Int) {
        requireValidIndices(i0, i1)
        data[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Int) {
        requireValidIndices(i0, i1, i2)
        data[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3)
        data[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int) {
        requireValidIndices(i0, i1, i2, i3, i4)
        data[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int) {
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

    override fun setValue(value: IntArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is NumerikoIntArray && value.backingArray !== this.data)
            value
        else
            value.copy()

        source.unsafeForEachIndexed { indices, element ->
            setInt(indices, element)
        }

    }

    override fun copy(): IntArrayND =
            if (fullData)
                NumerikoIntArrayND(
                        shape = shape.copy(),
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super.copy()

}
