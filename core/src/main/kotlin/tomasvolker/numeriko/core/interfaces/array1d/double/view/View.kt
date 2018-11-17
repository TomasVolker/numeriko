package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex

class DefaultMutableDoubleArray1DView (
        val array: MutableDoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableDoubleArray1D() {

    init {
        array.requireValidIndices(convertIndex(0))
        array.requireValidIndices(convertIndex(lastIndex))
    }

    override fun setDouble(value: Double, index: Int) {
        requireValidIndices(index)
        array.setDouble(value, offset + stride * index)
    }

    override fun getDouble(index: Int): Double {
        requireValidIndices(index)
        return array.getDouble(offset + stride * index)
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

}

fun defaultDoubleArray1DView(array: MutableDoubleArray1D, i0: IntProgression) =
        DefaultMutableDoubleArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
