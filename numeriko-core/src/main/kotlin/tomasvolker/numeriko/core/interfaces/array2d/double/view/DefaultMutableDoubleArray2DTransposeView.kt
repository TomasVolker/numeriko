package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

class DefaultMutableDoubleArray2DTransposeView(
        val array: MutableDoubleArray2D
) : DefaultMutableDoubleArray2D() {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override operator fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.getDouble(i1, i0)
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        return array.setDouble(i1, i0, value)
    }

}

