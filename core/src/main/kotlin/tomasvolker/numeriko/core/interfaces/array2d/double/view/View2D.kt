package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.checkIndices

open class DefaultDoubleArray2DView(
        open val array: DoubleArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DefaultDoubleArray2D() {

    override fun getDouble(i0: Int, i1: Int): Double {
        checkIndices(i0, i1)

        return array.getValue(
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

}

class DefaultMutableDoubleArray2DView(
        override val array: MutableDoubleArray2D,
        offset0: Int,
        offset1: Int,
        shape0: Int,
        shape1: Int,
        stride0: Int,
        stride1: Int
) : DefaultDoubleArray2DView(
        array,
        offset0,
        offset1,
        shape0,
        shape1,
        stride0,
        stride1
), MutableDoubleArray2D {

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        checkIndices(i0, i1)

        array.setValue(
                value,
                offset0 + stride0 * i0,
                offset1 + stride1 * i1
        )
    }

}
