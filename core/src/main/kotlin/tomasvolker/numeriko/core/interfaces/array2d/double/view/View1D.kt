package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D


class MutableDoubleArray2DCollapseView(
        val array: MutableDoubleArray2D
) : DefaultMutableDoubleArray1D() {

    val axis: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return when(axis) {
            0 -> array.getValue(
                    i0,
                    0
            )
            1 -> array.getValue(
                    0,
                    i0
            )
            else -> throw IllegalStateException()
        }
    }

    override fun setDouble(value: Double, i0: Int) {
        when(axis) {
            0 -> array.setDouble(
                    value,
                    i0,
                    0
            )
            1 -> array.setDouble(
                    value,
                    0,
                    i0
            )
            else -> throw IllegalStateException()
        }
    }

}

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: IntProgression, i1: Int) =
        MutableDoubleArray2DCollapseView(
            DefaultMutableDoubleArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1,
                shape0 = i0.count(),
                shape1 = 1,
                stride0 = i0.step,
                stride1 = 1
        )
)

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: Int, i1: IntProgression) =
        MutableDoubleArray2DCollapseView(
                DefaultMutableDoubleArray2DView(
                        array = array,
                        offset0 = i0,
                        offset1 = i1.first,
                        shape0 = 1,
                        shape1 = i1.count(),
                        stride0 = 1,
                        stride1 = i1.step
                )
        )
