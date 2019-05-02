package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableDoubleArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.DoubleBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DefaultMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.illegalArgument
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.view.ContiguousLastAxis

class BufferDoubleArrayND(
        override val shape: IntArray1D,
        override val buffer: DoubleBuffer,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableDoubleArrayND(), LinearlyBackedMutableDoubleArrayND<DoubleBuffer> {

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getSlice(slice: ArraySlice): BufferDoubleArrayND =
            BufferDoubleArrayND(
                shape = slice.shape.toIntArray1D(),
                buffer = buffer,
                offset = convertIndices(slice.origin),
                strideArray = IntArray(slice.shape.size) { a ->
                    val permuted = slice.permutation[a]
                    if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                }
            )

    override fun canReshapeTo(shape: IntArray1D): Boolean =
            super<DefaultMutableDoubleArrayND>.canReshapeTo(shape) ||
                    shape.product() == size && isContiguous

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): DoubleArrayND {
        require(shape.product() == size) { "$size elements in ${this.shape} cannot be reshaped to ${shape.product()} elements in $shape" }
        return when {
            canSliceReshapeTo(shape) -> sliceReshape(shape)
            isContiguous && shape.product() == size ->
                BufferDoubleArrayND(
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


    override fun copy(): DoubleArrayND =
            when {
                fullData ->
                    BufferDoubleArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(),
                            offset = offset,
                            strideArray = strideArray.copyOf()
                    )
                isContiguous ->
                    BufferDoubleArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(offset, size),
                            offset = 0,
                            strideArray = strideArray.copyOf()
                    )
                else ->super<DefaultMutableDoubleArrayND>.copy()
            }

    override fun setValue(value: ArrayND<Double>) {
        requireSameShape(this, value)

        if (value is BufferDoubleArrayND) {

            if (this.order == value.order) {
                value.buffer.copyInto(
                        destination = this.buffer,
                        destinationOffset = this.offset,
                        startIndex = value.offset,
                        endIndex = value.size
                )
                return
            }

        }

        return super<DefaultMutableDoubleArrayND>.setValue(value)
    }

}
