package tomasvolker.numeriko.core.implementations.array.arraynd

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.implementations.array.LinearlyBackedMutableArrayND
import tomasvolker.numeriko.core.implementations.array.buffer.Buffer
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndexed
import tomasvolker.numeriko.core.interfaces.slicing.ArraySlice
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.view.ContiguousLastAxis

class ArrayGenericArrayND<T>(
        override val shape: IntArray1D,
        override val buffer: Buffer<T>,
        override val offset: Int = 0,
        override val strideArray: IntArray = ContiguousLastAxis.strideArray(shape.toIntArray())
): DefaultMutableArrayND<T>(), LinearlyBackedMutableArrayND<T, Buffer<T>> {

    override val rank: Int
        get() = shape.size

    val fullData: Boolean = shape.product() == buffer.size

    // Performance
    private val arrayShape: IntArray = shape.toIntArray()
    override fun shape(axis: Int): Int = arrayShape[axis]

    override fun getSlice(slice: ArraySlice): ArrayGenericArrayND<T> =
            ArrayGenericArrayND(
                    shape = slice.shape.toIntArray1D(),
                    buffer = buffer,
                    offset = convertIndices(slice.origin),
                    strideArray = IntArray(slice.shape.size) { a ->
                        val permuted = slice.permutation[a]
                        if (permuted < 0) 1 else  slice.strides[a] * strideArray[permuted]
                    }
            )



    override fun copy(): ArrayND<T> =
            if (fullData)
                ArrayGenericArrayND(
                        shape = shape.copy(),
                        buffer = buffer.copy(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super<DefaultMutableArrayND>.copy()

}

