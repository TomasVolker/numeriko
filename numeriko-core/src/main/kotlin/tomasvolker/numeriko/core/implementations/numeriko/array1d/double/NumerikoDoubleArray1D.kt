package tomasvolker.numeriko.core.implementations.numeriko.array1d.double

import tomasvolker.numeriko.core.implementations.numeriko.array0d.double.NumerikoDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex

class NumerikoDoubleArray1D(
        val data: DoubleArray
): DefaultMutableDoubleArray1D() {

    override val size: Int get() = data.size

    override operator fun get(i0: Int): Double = data[i0]

    override operator fun set(i0: Int, value: Double) {
        data[i0] = value
    }

    override fun getView(i0: IntProgression): MutableDoubleArray1D {
        requireValidIndexRange(i0)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArray0D {
        require(shape(axis) == 1)
        return NumerikoDoubleArray0DView(data, 0)
    }

}


class NumerikoMutableDoubleArray1DView(
        val data: DoubleArray,
        val offset: Int,
        override val size: Int,
        val stride: Int
): DefaultMutableDoubleArray1D() {

    init {
        require(offset in 0 until data.size)
        require(convertIndex(lastIndex) in 0 until data.size)
    }

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return data[convertIndex(i0)]
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        data[convertIndex(i0)] = value
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

    override fun getView(i0: IntProgression): MutableDoubleArray1D {
        requireValidIndexRange(i0)
        return NumerikoMutableDoubleArray1DView(
                data = data,
                offset = convertIndex(i0.first),
                size = i0.count(),
                stride = stride * i0.step
        )
    }

    override fun lowerRank(axis: Int): MutableDoubleArray0D {
        require(shape(axis) == 1)
        return NumerikoDoubleArray0DView(data, offset)
    }

}