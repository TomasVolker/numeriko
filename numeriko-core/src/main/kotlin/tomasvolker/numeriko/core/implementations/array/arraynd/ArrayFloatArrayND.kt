package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.ArrayFloatArray
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.DefaultMutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class ArrayFloatArrayND(
        override val shape: IntArray1D,
        override val data: FloatArray,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableFloatArrayND(), LinearlyBackedMutableFloatArrayND<FloatArray> {

    override fun linearGet(i: Int): Float = data[i]
    override fun linearSet(i: Int, value: Float) { data[i] = value }
    override val dataSize: Int get() = data.size

    val fullData: Boolean = shape.product() == data.size

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getSlice(slice: ArraySlice): ArrayFloatArrayND =
            ArrayFloatArrayND(
                    shape = slice.shape.toIntArray1D(),
                    data = data,
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
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableFloatArrayND>.copy()

}
