package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.ArrayDoubleArray
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DefaultMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class ArrayDoubleArrayND(
        override val shape: IntArray1D,
        override val data: DoubleArray,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableDoubleArrayND(), LinearlyBackedMutableDoubleArrayND<DoubleArray> {

    override fun linearGet(i: Int): Double = data[i]
    override fun linearSet(i: Int, value: Double) { data[i] = value }
    override val dataSize: Int get() = data.size

    val fullData: Boolean = shape.product() == data.size

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun getSlice(slice: ArraySlice): ArrayDoubleArrayND =
            ArrayDoubleArrayND(
                shape = slice.shape.toIntArray1D(),
                data = data,
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
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableDoubleArrayND>.copy()

}
