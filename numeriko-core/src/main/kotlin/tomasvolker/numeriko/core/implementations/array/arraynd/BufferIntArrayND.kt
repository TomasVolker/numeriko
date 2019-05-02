package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableIntArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.IntBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.DefaultMutableIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.canSliceReshapeTo
import tomasvolker.numeriko.core.interfaces.slicing.sliceReshape
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.elementOrderOf

class BufferIntArrayND(
        shape: IntArray1D,
        override val buffer: IntBuffer,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableIntArrayND(), LinearlyBackedMutableIntArrayND<IntBuffer> {

    // Performance
    private val shapeArray: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = shapeArray[axis]
    override val shape: IntArray1D = shapeArray.asIntArray1D()

    override val order: ElementOrder? get() = elementOrderOf(shapeArray, strideArray)

    override fun getSlice(slice: ArraySlice): BufferIntArrayND =
            BufferIntArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )

    override fun canReshapeTo(shape: IntArray1D): Boolean =
            super<DefaultMutableIntArrayND>.canReshapeTo(shape) ||
                    shape.product() == size && isContiguous

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): IntArrayND {
        require(shape.product() == size) { "$size elements in ${this.shape} cannot be reshaped to ${shape.product()} elements in $shape" }
        return when {
            canSliceReshapeTo(shape) -> sliceReshape(shape)
            isContiguous && shape.product() == size ->
                BufferIntArrayND(
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


    override fun copy(): IntArrayND =
            when {
                fullData ->
                    BufferIntArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(),
                            offset = offset,
                            strideArray = strideArray.copyOf()
                    )
                isContiguous ->
                    BufferIntArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(offset, size),
                            offset = 0,
                            strideArray = strideArray.copyOf()
                    )
                else ->super<DefaultMutableIntArrayND>.copy()
            }

}
