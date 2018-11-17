package tomasvolker.numeriko.core.implementations.numeriko.array2d.double

import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D

class NumerikoMutableDoubleArray2D(
        override val shape0: Int,
        override val shape1: Int,
        val data: DoubleArray
): DefaultMutableDoubleArray2D() {

    init {
        require(data.size == shape0 * shape1)
    }

    override val size: Int get() = data.size

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        data[i0 * shape1 + i1] = value
    }

    override fun getDouble(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return data[i0 * shape1 + i1]
    }

}