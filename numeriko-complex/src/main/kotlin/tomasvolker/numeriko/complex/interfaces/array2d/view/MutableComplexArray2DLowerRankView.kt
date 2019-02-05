package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.core.interfaces.array2d.numeric.MutableNumericArray2D

class MutableComplexArray2DLowerRankView(
        val array: MutableComplexArray2D,
        val axis: Int
) : DefaultMutableComplexArray1D() {

    init {
        require(array.shape(axis) <= 1)
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
            0 -> array.setImag(value, i0, 0)
            1 -> array.setImag(value, 0, i0)
            else -> throw IllegalStateException()
        }
    }

    override fun higherRank(axis: Int): MutableNumericArray2D<Complex> = TODO()

}

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: Int, i1: Int) =
        defaultComplexArray2DView(array, i0..i0, i1..i1).lowerRank(axis = 0).lowerRank(axis = 0)

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: Int, i1: IntProgression) =
        defaultComplexArray2DView(array, i0..i0, i1).lowerRank(axis = 0)

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: IntProgression, i1: Int) =
        defaultComplexArray2DView(array, i0, i1..i1).lowerRank(axis = 1)
