package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.checkIndices

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
        checkIndices(i0, i1)

        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        checkIndices(i0, i1)

        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

}
