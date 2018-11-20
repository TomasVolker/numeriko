package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D


class MutableDoubleArray2DLowerRankView(
        val array: MutableDoubleArray2D,
        val axis: Int
) : DefaultMutableDoubleArray1D() {

    init {
        require(array.shape(axis) <= 1)
    }

    override val size: Int get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return when(axis) {
            0 -> array.getValue(i0, 0)
            1 -> array.getValue(0, i0)
            else -> throw IllegalStateException()
        }
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        when(axis) {
            0 -> array.setDouble(value, i0, 0)
            1 -> array.setDouble(value, 0, i0)
            else -> throw IllegalStateException()
        }
    }

}

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: Int, i1: Int) =
        defaultDoubleArray2DView(array, i0..i0, i1..i1).lowerRank(axis = 0).lowerRank(axis = 0)

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: Int, i1: IntProgression) =
        defaultDoubleArray2DView(array, i0..i0, i1).lowerRank(axis = 0)

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: IntProgression, i1: Int) =
            defaultDoubleArray2DView(array, i0, i1..i1).lowerRank(axis = 1)

