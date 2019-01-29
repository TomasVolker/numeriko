package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2D


class DefaultDoubleArray1DHigherRankView (
        val array: MutableDoubleArray1D,
        val axis: Int
) : DefaultMutableDoubleArray2D() {

    init {
        require(axis in 0..1)
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

    override operator fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return when (axis) {
            0 -> array.getDouble(i1)
            1 -> array.getDouble(i0)
            else -> error("requireValidIndices should fail")
        }
    }

    override operator fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        when (axis) {
            0 -> array.setDouble(i1, value)
            1 -> array.setDouble(i0, value)
            else -> error("requireValidIndices should fail")
        }
    }

}
