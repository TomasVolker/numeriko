package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D


class DefaultDoubleArray1DHigherRankView (
        val array: MutableDoubleArray1D,
        val axis: Int
) : DefaultMutableDoubleArray2D() {

    init {
        require(axis <= 1)
    }

    override val shape0: Int
        get() = when(axis) {
            0 -> 1
            1 -> array.size
            else -> error("")
        }

    override val shape1: Int
        get() = when(axis) {
            0 -> array.size
            1 -> 1
            else -> error("")
        }

    override fun getDouble(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return when (axis) {
            0 -> array.getDouble(0, i1)
            1 -> array.getDouble(i0, 0)
            else -> error("requireValidIndices should fail")
        }
    }

    override fun setDouble(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        when (axis) {
            0 -> array.setDouble(value, 0, i1)
            1 -> array.setDouble(value, i0, 0)
            else -> error("requireValidIndices should fail")
        }
    }

}
