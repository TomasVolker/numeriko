package tomasvolker.numeriko.lowrank.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.MutableArray2D

class DefaultArray2DLowerRankView<T>(
        val array: MutableArray2D<T>,
        val axis: Int
) : DefaultMutableArray1D<T>() {

    init {
        require(array.shape(axis) <= 1)
    }

    override val size: Int get() = array.size

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return when {
            axis == 0 -> array.getValue(0, i0)
            axis == 1 -> array.getValue(i0, 0)
            else -> throw IllegalStateException()
        }
    }

    override fun setValue(i0: Int, value: T) {
        when(axis) {
            0 -> array.setValue(0, i0, value)
            1 -> array.setValue(i0, 0, value)
            else -> throw IllegalStateException()
        }
    }

}

fun <T> defaultArray2DView(array: MutableArray2D<T>, i0: Int, i1: Int) =
        defaultArray2DView(array, i0..i0, i1..i1).lowerRank(0).lowerRank(0)

fun <T> defaultArray2DView(array: MutableArray2D<T>, i0: Int, i1: IntProgression) =
        defaultArray2DView(array, i0..i0, i1).lowerRank(0)

fun <T> defaultArray2DView(array: MutableArray2D<T>, i0: IntProgression, i1: Int) =
        defaultArray2DView(array, i0, i1..i1).lowerRank(1)
