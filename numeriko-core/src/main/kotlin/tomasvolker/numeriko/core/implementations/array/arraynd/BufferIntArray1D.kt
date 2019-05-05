package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.implementations.array.LinearlyBackedIntArrayND
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableIntArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.IntBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.DefaultMutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.DefaultMutableIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex1
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*
import java.lang.IndexOutOfBoundsException

class BufferIntArray1D(
        override val buffer: IntBuffer,
        override val size: Int = buffer.size,
        override val offset: Int = 0,
        val stride: Int = 1
): DefaultMutableIntArray1D(), LinearlyBackedMutableIntArrayND<IntBuffer> {

    override val strideArray: IntArray get() = intArrayOf(stride)

    override fun slice(slice: ArraySlice): BufferIntArrayND =
            BufferIntArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin[0]),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * stride
                    }
            )

    override fun get(): Nothing = rankError(1)
    override fun get(i0: Int, i1: Int): Nothing = rankError(1)
    override fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(1)
    override fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(1)
    override fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(1)
    override fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(1)

    override fun getValue(indices: IntArray): Int = getInt(indices)
    override fun getInt(indices: IntArray): Int = super<DefaultMutableIntArray1D>.getInt(indices)

    override fun set(value: Int): Nothing = rankError(1)
    override operator fun set(i0: Int, i1: Int, value: Int): Nothing = rankError(1)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Int): Nothing = rankError(1)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Int): Nothing = rankError(1)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Int): Nothing = rankError(1)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Int): Nothing = rankError(1)

    override fun setInt(indices: IntArray, value: Int) { super<DefaultMutableIntArray1D>.setInt(indices, value) }

    override operator fun set(vararg indices: Int, value: Int) = setInt(indices, value)

    override operator fun get(vararg indices: Int): Int = getInt(indices)

    override operator fun get(i0: Int): Int {
        if(i0 !in 0 until size) throw IndexOutOfBoundsException("Index $i0 on axis 0 is out of size $size")
        return buffer[convertIndices(i0)]
    }

    override operator fun set(i0: Int, value: Int) {
        if(i0 !in 0 until size) throw IndexOutOfBoundsException("Index $i0 on axis 0 is out of size $size")
        buffer[convertIndices(i0)] = value
    }

    override fun convertIndices(i0: Int): Int = offset + stride * i0

    override fun setValue(value: IntArrayND) =
            super<LinearlyBackedMutableIntArrayND>.setValue(value)

    override fun copy(): IntArray1D =
            when {
                fullData ->
                    BufferIntArray1D(
                            size = size,
                            buffer = buffer.copy(),
                            offset = offset,
                            stride = stride
                    )
                isContiguous ->
                    BufferIntArray1D(
                            size = size,
                            buffer = buffer.copy(offset, size),
                            offset = 0,
                            stride = stride
                    )
                else ->super<DefaultMutableIntArray1D>.copy()
            }

}
