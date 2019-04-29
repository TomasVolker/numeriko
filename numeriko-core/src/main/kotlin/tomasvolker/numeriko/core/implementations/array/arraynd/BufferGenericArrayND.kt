package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.Buffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.interfaces.slicing.canSliceReshapeTo
import tomasvolker.numeriko.core.interfaces.slicing.sliceReshape
import tomasvolker.numeriko.core.preconditions.illegalArgument
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.elementOrderOf

class BufferGenericArrayND<T>(
        shape: IntArray1D,
        override val buffer: Buffer<T>,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableArrayND<T>(), LinearlyBackedMutableArrayND<T, Buffer<T>> {

    override val rank: Int
        get() = shape.size

    // Performance
    private val shapeArray: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = shapeArray[axis]
    override val shape: IntArray1D get() = shapeArray.asIntArray1D()

    override val order: ElementOrder? get() = elementOrderOf(shapeArray, strideArray)

    override fun getSlice(slice: ArraySlice): BufferGenericArrayND<T> =
            BufferGenericArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )

    override fun canReshapeTo(shape: IntArray1D): Boolean =
            super<DefaultMutableArrayND>.canReshapeTo(shape) || isContiguous

    override fun reshape(shape: IntArray1D, copyIfNecessary: Boolean): ArrayND<T> =
            when {
                canSliceReshapeTo(shape) -> sliceReshape(shape)
                isContiguous ->
                    BufferGenericArrayND(
                            shape = shape,
                            buffer = buffer,
                            offset = offset,
                            strideArray = order?.strideArray(shape.toIntArray()) ?: error("Contiguous array must have order")
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


    override fun copy(): ArrayND<T> =
            when {
                fullData ->
                    BufferGenericArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(),
                            offset = offset,
                            strideArray = strideArray.copyOf()
                    )
                isContiguous ->
                    BufferGenericArrayND(
                            shape = shape.copy(),
                            buffer = buffer.copy(offset, size),
                            offset = 0,
                            strideArray = strideArray.copyOf()
                    )
                else ->super<DefaultMutableArrayND>.copy()
            }

}

