package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableByteArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.ByteArrayBuffer
import tomasvolker.numeriko.core.implementations.array.buffer.ByteBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.byte.DefaultMutableByteArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.illegalArgument
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.elementOrderOf

class BufferByteArrayND(
        shape: IntArray1D,
        override val buffer: ByteBuffer,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableByteArrayND(), LinearlyBackedMutableByteArrayND<ByteBuffer> {

    // Performance
    private val shapeArray: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = shapeArray[axis]
    override val shape: IntArray1D = shapeArray.asIntArray1D()

    override val order: ElementOrder? get() = elementOrderOf(shapeArray, strideArray)

    override fun slice(slice: ArraySlice): BufferByteArrayND =
            BufferByteArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )

    override fun canReshapeTo(shape: IntArray1D): Boolean =
            super<DefaultMutableByteArrayND>.canReshapeTo(shape) ||
            shape.product() == size && isContiguous

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): ByteArrayND {
        require(shape.product() == size) { "$size elements in ${this.shape} cannot be reshaped to ${shape.product()} elements in $shape" }
        return when {
            canSliceReshapeTo(shape) -> sliceReshape(shape)
            isContiguous ->
                BufferByteArrayND(
                        shape = shape,
                        buffer = buffer,
                        offset = offset,
                        strideArray = order?.strideArray(shape.toIntArray())
                                ?: error("Contiguous array must have order")
                )
            else -> {

                val copy = if (copyIfNecessary)
                    copy()
                else
                    illegalArgument("Cannot reshape ${this.shape} to $shape without copying")

                if (!copy.canReshapeTo(shape))
                    error("Cannot reshape a copy of the array")

                copy.reshape(shape, copyIfNecessary = false)
            }
        }
    }

    override fun rawBytes(): java.nio.ByteBuffer =
            if (buffer is ByteArrayBuffer && order == ContiguousLastAxis && offset == 0)
                java.nio.ByteBuffer.wrap(buffer.array).asReadOnlyBuffer()
            else
                super<DefaultMutableByteArrayND>.rawBytes()

    override fun copy(): ByteArrayND =
            when {
                fullData ->
                    BufferByteArrayND(
                        shape = shape.copy(),
                        buffer = buffer.copy(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                    )
                isContiguous ->
                    BufferByteArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(offset, size),
                            offset = 0,
                            strideArray = strideArray.copyOf()
                    )
                else ->super<DefaultMutableByteArrayND>.copy()
            }


}
