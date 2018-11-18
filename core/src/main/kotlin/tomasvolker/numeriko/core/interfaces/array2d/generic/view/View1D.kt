package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D

class Array2DCollapseView<T>(
        val array: MutableArray2D<T>
) : DefaultMutableArray1D<T>() {

    val axis: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return when {
            axis == 0 -> array.getValue(
                    i0,
                    0
            )
            axis == 1 -> array.getValue(
                    0,
                    i0
            )
            else -> throw IllegalStateException()
        }
    }

    override fun setValue(value: T, i0: Int) {
        when(axis) {
            0 -> array.setValue(
                    value,
                    i0,
                    0
            )
            1 -> array.setValue(
                    value,
                    0,
                    i0
            )
            else -> throw IllegalStateException()
        }
    }

}
