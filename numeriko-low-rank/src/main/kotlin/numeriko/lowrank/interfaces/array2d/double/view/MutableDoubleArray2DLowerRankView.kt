package numeriko.lowrank.interfaces.array2d.double.view

import numeriko.lowrank.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import numeriko.lowrank.interfaces.array2d.double.MutableDoubleArray2D


class MutableDoubleArray2DLowerRankView(
        val array: MutableDoubleArray2D,
        val axis: Int
) : DefaultMutableDoubleArray1D() {

    init {
        require(array.shape(axis) <= 1)
    }

    override val size: Int get() = array.size

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return when(axis) {
            0 -> array.getValue(0, i0)
            1 -> array.getValue(i0, 0)
            else -> throw IllegalStateException()
        }
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        when(axis) {
            0 -> array.setDouble(0, i0, value)
            1 -> array.setDouble(i0, 0, value)
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

