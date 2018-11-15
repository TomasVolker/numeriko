package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.checkIndices

class DefaultMutableDoubleArray2DTransposeView(
        val array: MutableDoubleArray2D
) : DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override fun getDouble(i0: Int, i1: Int): Double {
        checkIndices(i0, i1)
        return array.getDouble(i1, i0)
    }

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        checkIndices(i0, i1)
        return array.setDouble(value, i1, i0)
    }

    override fun transpose(): MutableDoubleArray2D = array

}

