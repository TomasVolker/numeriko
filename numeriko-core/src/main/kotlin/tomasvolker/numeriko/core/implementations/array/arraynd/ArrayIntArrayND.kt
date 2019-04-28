package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.ArrayIntArray
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableIntArrayND
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.DefaultMutableIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.MutableIntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class ArrayIntArrayND(
        override val shape: IntArray1D,
        override val data: IntArray,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableIntArrayND(), LinearlyBackedMutableIntArrayND<IntArray> {

    override fun linearGet(i: Int): Int = data[i]
    override fun linearSet(i: Int, value: Int) { data[i] = value }
    override val dataSize: Int get() = data.size

    val fullData: Boolean = shape.product() == data.size

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getSlice(slice: ArraySlice): ArrayIntArrayND =
            ArrayIntArrayND(
                    shape = slice.shape.toIntArray1D(),
                    data = data,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )

    override fun copy(): IntArrayND =
            if (fullData)
                ArrayIntArrayND(
                        shape = shape.copy(),
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableIntArrayND>.copy()

}