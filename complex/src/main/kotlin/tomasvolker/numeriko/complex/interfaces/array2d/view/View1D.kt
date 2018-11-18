package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D


class MutableComplexArray2DCollapseView(
        val array: MutableComplexArray2D
) : DefaultMutableComplexArray1D() {

    val axis: Int = when {
        array.shape0 == 1 -> 1
        array.shape1 == 1 -> 0
        else -> throw IllegalArgumentException("array is not flat")
    }

    override val size: Int get() = array.size

    override fun real(i0: Int): Double {
        requireValidIndices(i0)
        return when(axis) {
            0 -> array.real(i0, 0)
            1 -> array.real(0, i0)
            else -> throw IllegalStateException()
        }
    }

    override fun imag(i0: Int): Double {
        requireValidIndices(i0)
        return when(axis) {
            0 -> array.imag(i0, 0)
            1 -> array.imag(0, i0)
            else -> throw IllegalStateException()
        }
    }

    override fun setReal(value: Double, i0: Int) {
        requireValidIndices(i0)
        when(axis) {
            0 -> array.setReal(value, i0, 0)
            1 -> array.setReal(value, 0, i0)
            else -> throw IllegalStateException()
        }
    }

    override fun setImag(value: Double, i0: Int) {
        requireValidIndices(i0)
        when(axis) {
            0 -> array.setReal(value, i0, 0)
            1 -> array.setReal(value, 0, i0)
            else -> throw IllegalStateException()
        }
    }

}

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: IntProgression, i1: Int) =
        MutableComplexArray2DCollapseView(
            DefaultMutableComplexArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1,
                shape0 = i0.count(),
                shape1 = 1,
                stride0 = i0.step,
                stride1 = 1
        )
)

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: Int, i1: IntProgression) =
        MutableComplexArray2DCollapseView(
                DefaultMutableComplexArray2DView(
                        array = array,
                        offset0 = i0,
                        offset1 = i1.first,
                        shape0 = 1,
                        shape1 = i1.count(),
                        stride0 = 1,
                        stride1 = i1.step
                )
        )
