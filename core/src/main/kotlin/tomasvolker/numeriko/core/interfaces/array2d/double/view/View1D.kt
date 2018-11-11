package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D


class MutableDoubleArray2DCollapseView(
        val array: MutableDoubleArray2D
) : DefaultMutableDoubleArray1D() {

    val dimension: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun getDouble(index: Int): Double {
        return when(dimension) {
            0 -> array.getValue(
                    index,
                    0
            )
            1 -> array.getValue(
                    0,
                    index
            )
            else -> throw IllegalStateException()
        }
    }

    override fun setDouble(value: Double, index: Int) {
        when(dimension) {
            0 -> array.setValue(
                    value,
                    index,
                    0
            )
            1 -> array.setValue(
                    value,
                    0,
                    index
            )
            else -> throw IllegalStateException()
        }
    }

}
