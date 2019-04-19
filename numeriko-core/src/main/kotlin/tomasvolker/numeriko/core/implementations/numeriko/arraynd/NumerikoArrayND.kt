package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class NumerikoArrayND<T>(
        override val shape: IntArrayND,
        val data: Array<T>,
        val offset: Int = 0,
        val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableArrayND<T>() {

    override val rank: Int
        get() = shape.size

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun getSlice(slice: ArraySlice): NumerikoArrayND<T> =
            NumerikoArrayND(
                    shape = slice.shape.toIntArrayND(),
                    data = data,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[slice.permutation[a]]
                    }
            )

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

}

