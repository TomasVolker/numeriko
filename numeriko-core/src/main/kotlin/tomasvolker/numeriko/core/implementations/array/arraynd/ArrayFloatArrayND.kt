package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableFloatArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.FloatBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.DefaultMutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.factory.asIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.view.ContiguousFirstAxis
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.elementOrderOf

class ArrayFloatArrayND(
        shape: IntArray1D,
        override val buffer: FloatBuffer,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableFloatArrayND(), LinearlyBackedMutableFloatArrayND<FloatBuffer> {

    // Performance
    private val shapeArray: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = shapeArray[axis]
    override val shape: IntArray1D = shapeArray.asIntArray1D()

    val fullData: Boolean get() = this.size == buffer.size
    val isContiguous: Boolean get() = order != null
    val order: ElementOrder? = elementOrderOf(shapeArray, strideArray)

    override fun getSlice(slice: ArraySlice): ArrayFloatArrayND =
            ArrayFloatArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )

    override fun copy(): FloatArrayND =
            if (fullData)
                ArrayFloatArrayND(
                        shape = shape.copy(),
                        buffer = buffer.copy(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableFloatArrayND>.copy()
/*
    fun reshape(shape: IntArray1D): ArrayFloatArrayND {

        require(shape.product() == this.size)

        if (order != null) {

            return ArrayFloatArrayND(
                    shape = shape,
                    buffer = this.buffer,
                    offset = this.offset,
                    strideArray = order.strideArray(shape.toIntArray())
            )

        }

    }
*/
}
