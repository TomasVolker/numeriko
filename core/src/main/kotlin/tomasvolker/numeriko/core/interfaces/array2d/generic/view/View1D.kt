package tomasvolker.numeriko.core.interfaces.array2d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D

class Array2DCollapseView<out T>(
        val array: Array2D<T>
) : DefaultArray1D<T>() {

    val dim: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun getValue(index: Int): T {

        return when {
            dim == 0 -> array.getValue(
                    index,
                    0
            )
            dim == 1 -> array.getValue(
                    0,
                    index
            )
            else -> throw IllegalStateException()
        }
    }

}
