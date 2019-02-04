package tomasvolker.numeriko.core.implementations.numeriko.array2d.double

import tomasvolker.numeriko.core.implementations.numeriko.NumerikoDoubleArray
import tomasvolker.numeriko.core.implementations.numeriko.array1d.double.NumerikoMutableDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

class NumerikoDoubleArray2D(
        override val shape0: Int,
        override val shape1: Int,
        val data: DoubleArray
): DefaultMutableDoubleArray2D(), NumerikoDoubleArray {

    init {
        require(data.size == shape0 * shape1)
    }

    override val backingArray: DoubleArray
        get() = data

    override val size: Int get() = data.size

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        data[linearIndex(i0, i1)] = value
    }

    override operator fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return data[linearIndex(i0, i1)]
    }

    fun linearIndex(i0: Int, i1: Int): Int = i0 * shape1 + i1

    override fun getView(i0: IntProgression, i1: IntProgression): MutableDoubleArray2D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoDoubleArray2DView(
                data = data,
                offset = linearIndex(i0.first, i1.first),
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = shape1 * i0.step,
                stride1 = i1.step
        )
    }

    override fun getView(i0: Int, i1: IntProgression): MutableDoubleArray1D {
        requireValidIndex(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = linearIndex(i0, i1.first),
                size = i1.count(),
                stride = i1.step
        )
    }

    override fun getView(i0: IntProgression, i1: Int): MutableDoubleArray1D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndex(i1, axis = 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = linearIndex(i0.first, i1),
                size = i0.count(),
                stride = shape1 * i0.step
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArray1D {
        require(shape(axis) <= 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = 0,
                size = shape(axis),
                stride = if (axis == 0) shape1 else 1
        )
    }

    override fun setValue(value: DoubleArray2D) {
        requireSameShape(this, value)

        // Anti alias copy
        val source = if (value is NumerikoDoubleArray && value.backingArray != this.data)
            value
        else
            value.copy()

        source.inlinedForEachIndexed { i0, i1, element ->
            setDouble(i0, i1, element)
        }

    }

}

