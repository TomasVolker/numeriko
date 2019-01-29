package tomasvolker.numeriko.core.implementations.numeriko.array2d.double

import tomasvolker.numeriko.core.implementations.numeriko.array1d.double.NumerikoMutableDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex0
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex1

class NumerikoDoubleArray2D(
        override val shape0: Int,
        override val shape1: Int,
        val data: DoubleArray
): DefaultMutableDoubleArray2D() {

    init {
        require(data.size == shape0 * shape1)
    }

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
        return NumerikoMutableDoubleArray2DView(
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

}

class NumerikoMutableDoubleArray2DView(
        val data: DoubleArray,
        val offset: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
): DefaultMutableDoubleArray2D() {

    init {
        require(linearIndex(0, 0) in 0 until data.size)
        require(linearIndex(0, lastIndex1) in 0 until data.size)
        require(linearIndex(lastIndex0, 0) in 0 until data.size)
        require(linearIndex(lastIndex0, lastIndex1) in 0 until data.size)
    }

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
        return NumerikoMutableDoubleArray2DView(
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

}