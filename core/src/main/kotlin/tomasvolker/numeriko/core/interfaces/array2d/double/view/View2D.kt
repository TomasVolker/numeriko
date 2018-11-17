package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

class DefaultMutableDoubleArray2DView(
        val array: MutableDoubleArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DefaultMutableDoubleArray2D() {

    override fun getDouble(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

}

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: IntProgression, i1: IntProgression) =
        DefaultMutableDoubleArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1.first,
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = i0.step,
                stride1 = i1.step
        )