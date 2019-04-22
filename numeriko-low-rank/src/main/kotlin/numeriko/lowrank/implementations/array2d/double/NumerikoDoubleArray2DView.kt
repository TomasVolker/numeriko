package numeriko.lowrank.implementations.array2d.double

import tomasvolker.core.implementations.numeriko.NumerikoDoubleArray
import tomasvolker.core.implementations.numeriko.array1d.double.NumerikoMutableDoubleArray1DView
import numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import numeriko.lowrank.interfaces.array2d.double.MutableDoubleArray2D
import numeriko.lowrank.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import numeriko.lowrank.interfaces.array2d.generic.lastIndex0
import numeriko.lowrank.interfaces.array2d.generic.lastIndex1
import tomasvolker.core.preconditions.requireSameShape

class NumerikoDoubleArray2DView(
        val data: DoubleArray,
        val offset: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
): DefaultMutableDoubleArray2D(), NumerikoDoubleArray {

    init {
        require(linearIndex(0, 0) in 0 until data.size)
        require(linearIndex(0, lastIndex1) in 0 until data.size)
        require(linearIndex(lastIndex0, 0) in 0 until data.size)
        require(linearIndex(lastIndex0, lastIndex1) in 0 until data.size)
    }

    override val backingArray: DoubleArray
        get() = data

    override operator fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return data[linearIndex(i0, i1)]
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        data[linearIndex(i0, i1)] = value
    }

    override fun getView(i0: IntProgression, i1: IntProgression): MutableDoubleArray2D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoDoubleArray2DView(
                data = data,
                offset = linearIndex(i0.first, i1.first),
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = stride0 * i0.step,
                stride1 = stride1 * i1.step
        )
    }

    override fun getView(i0: Int, i1: IntProgression): MutableDoubleArray1D {
        requireValidIndex(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = linearIndex(i0, i1.first),
                size = i1.count(),
                stride = stride1 * i1.step
        )
    }

    override fun getView(i0: IntProgression, i1: Int): MutableDoubleArray1D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndex(i1, axis = 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = linearIndex(i0.first, i1),
                size = i0.count(),
                stride = stride0 * i0.step
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArray1D {
        require(shape(axis) <= 1)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = linearIndex(0, 0),
                size = shape(axis),
                stride = if (axis == 0) stride0 else stride1
        )
    }

    fun linearIndex(i0: Int, i1: Int): Int =
            offset + stride0 * i0 + stride1 * i1

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