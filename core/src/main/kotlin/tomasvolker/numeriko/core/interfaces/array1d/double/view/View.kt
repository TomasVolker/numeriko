package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D

class DefaultMutableDoubleArray1DView (
        val array: MutableDoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableDoubleArray1D() {

    override fun setDouble(value: Double, index: Int) {
        requireValidIndices(index)
        array.setDouble(value, offset + stride * index)
    }

    override fun getDouble(index: Int): Double {
        requireValidIndices(index)
        return array.getDouble(offset + stride * index)
    }

}

fun defaultDoubleArray1DView(array: MutableDoubleArray1D, i0: IntProgression) =
        DefaultMutableDoubleArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
