package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableDoubleArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.DoubleBuffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DefaultMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.view.ContiguousLastAxis

class ArrayDoubleArrayND(
        override val shape: IntArray1D,
        override val buffer: DoubleBuffer,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableDoubleArrayND(), LinearlyBackedMutableDoubleArrayND<DoubleBuffer> {

    val fullData: Boolean = shape.product() == buffer.size

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getSlice(slice: ArraySlice): ArrayDoubleArrayND =
            ArrayDoubleArrayND(
                shape = slice.shape.toIntArray1D(),
                buffer = buffer,
                offset = convertIndices(slice.origin),
                strideArray = IntArray(slice.shape.size) { a ->
                    val permuted = slice.permutation[a]
                    if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                }
            )

    override fun copy(): DoubleArrayND =
            if (fullData)
                ArrayDoubleArrayND(
                        shape = shape.copy(),
                        buffer = buffer.copy(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableDoubleArrayND>.copy()

}
