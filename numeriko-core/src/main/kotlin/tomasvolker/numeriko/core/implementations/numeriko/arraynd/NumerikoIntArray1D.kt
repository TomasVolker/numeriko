package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.implementations.numeriko.NumerikoIntArray
import tomasvolker.numeriko.core.interfaces.array1d.integer.DefaultMutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*

class NumerikoIntArray1D(
        val data: IntArray,
        override val size: Int = data.size,
        val offset: Int = 0,
        val stride: Int = 1
): DefaultMutableIntArray1D(), NumerikoIntArray {

    override val shape: IntArray1D
        get() = I[size]

    override val backingArray: IntArray
        get() = data

    override val rank: Int
        get() = shape.size

    val fullData: Boolean = this.size == data.size

    override fun getSlice(slice: ArraySlice): NumerikoIntArrayND =
            NumerikoIntArrayND(
                    shape = slice.shape.toIntArray1D(),
                    data = data,
                    offset = convertIndices(slice.origin[0]),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * stride
                    }
            )

    override fun shape(axis: Int): Int =
            if (axis == 0) size else throw IndexOutOfBoundsException("Axis $axis is out of bounds for rank 1")

    override operator fun get(i0: Int): Int {
        requireValidIndices(i0)
        return data[convertIndices(i0)]
    }

    override operator fun set(i0: Int, value: Int) {
        requireValidIndices(i0)
        data[convertIndices(i0)] = value
    }

    private fun convertIndices(i0: Int): Int = offset + stride * i0

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

    override fun copy(): IntArray1D =
            if (fullData)
                NumerikoIntArray1D(
                        data = data.copyOf(),
                        size = size,
                        offset = offset,
                        stride = stride
                )
            else
                super.copy()

}
