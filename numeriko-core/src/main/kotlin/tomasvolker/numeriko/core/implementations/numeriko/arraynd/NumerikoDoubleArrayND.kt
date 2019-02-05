package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.functions.DtoD
import tomasvolker.numeriko.core.functions.FunctionDtoD
import tomasvolker.numeriko.core.implementations.numeriko.NumerikoDoubleArray
import tomasvolker.numeriko.core.implementations.numeriko.factory.NumerikoArrayNDFactory
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.operations.reduction.product
import tomasvolker.numeriko.core.operations.reduction.remove
import tomasvolker.numeriko.core.preconditions.*
import tomasvolker.numeriko.core.view.ContiguousLastAxis
import tomasvolker.numeriko.core.view.linearIndex

class NumerikoDoubleArrayND(
        override val shape: IntArray1D,
        val data: DoubleArray,
        val offset: Int = 0,
        val strideArray: IntArray = ContiguousLastAxis.strideArray(shape)
): DefaultMutableDoubleArrayND(), NumerikoDoubleArray {

    override val backingArray: DoubleArray
        get() = data

    override val rank: Int
        get() = shape.size

    private val arrayShape = shape.toIntArray()

    val fullData: Boolean = shape.product() == data.size

    override fun shape(axis: Int): Int = arrayShape[axis]

    override val size: Int get() = data.size

    override fun get(): Double {
        requireRank(0)
        return data[offset]
    }

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return data[convertIndices(i0)]
    }

    override operator fun get(i0: Int, i1: Int): Double  {
        requireValidIndices(i0, i1)
        return data[convertIndices(i0, i1)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int): Double {
        requireValidIndices(i0, i1, i2)
        return data[convertIndices(i0, i1, i2)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Double {
        requireValidIndices(i0, i1, i2, i3)
        return data[convertIndices(i0, i1, i2, i3)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4)
        return data[convertIndices(i0, i1, i2, i3, i4)]
    }

    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Double {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        return data[convertIndices(i0, i1, i2, i3, i4, i5)]
    }

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun set(value: Double) {
        requireRank(0)
        data[offset] = value
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        data[convertIndices(i0)] = value
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        data[convertIndices(i0, i1)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double) {
        requireValidIndices(i0, i1, i2)
        data[convertIndices(i0, i1, i2)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3)
        data[convertIndices(i0, i1, i2, i3)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4)
        data[convertIndices(i0, i1, i2, i3, i4)] = value
    }

    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double) {
        requireValidIndices(i0, i1, i2, i3, i4, i5)
        data[convertIndices(i0, i1, i2, i3, i4, i5)] = value
    }

    override fun getView(vararg indices: IntProgression): MutableDoubleArrayND {
        requireRank(indices.size)
        for (axis in 0 until rank) {
            requireValidIndexRange(indices[axis], axis = axis)
        }
        return NumerikoDoubleArrayND(
                shape = intArray1D(rank) { axis -> indices[axis].count() },
                data = data,
                offset = convertIndices(IntArray(rank) { axis -> indices[axis].first }),
                strideArray = IntArray(rank) { axis -> indices[axis].step * strideArray[axis] }
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArrayND {
        require(shape(axis) <= 1)
        return NumerikoDoubleArrayND(
                shape = shape.remove(axis),
                data = data,
                offset = convertIndices(IntArray(rank) { 0 }),
                strideArray = IntArray(rank-1) { i -> if (i < axis) strideArray[i] else strideArray[i+1] }
        )
    }

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

    private fun convertIndices(i0: Int): Int =
            linearIndex(offset, strideArray, i0)

    private fun convertIndices(i0: Int, i1: Int): Int =
            linearIndex(offset, strideArray, i0, i1)

    private fun convertIndices(i0: Int, i1: Int, i2: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4)

    private fun convertIndices(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int =
            linearIndex(offset, strideArray, i0, i1, i2, i3, i4, i5)

    override fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is NumerikoDoubleArray && value.backingArray != this.data)
            value
        else
            value.copy()

        source.inlinedForEachIndexed { indices, element ->
            setDouble(indices, element)
        }

    }

    override fun copy(): DoubleArrayND =
            if (fullData)
                NumerikoDoubleArrayND(
                        shape = shape.copy(),
                        data = data.copyOf(),
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super.copy()

    override fun elementWise(function: FunctionDtoD) =
            inlineElementWise { function(it) }

    // TODO reimplement for views
    inline fun inlineElementWise(crossinline function: (Double)->Double) =
            if (fullData)
                NumerikoDoubleArrayND(
                        shape = shape.copy(),
                        data = DoubleArray(data.size) { i -> function(data[i]) },
                        offset = offset,
                        strideArray = strideArray.copyOf()
                )
            else
                super.elementWise(DtoD(function))

}
