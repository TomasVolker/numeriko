package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.lowrank.interfaces.array2d.numeric.MutableNumericArray2D

class DefaultComplexArray1DView (
        val array: MutableComplexArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableComplexArray1D() {

    override fun higherRank(axis: Int): MutableNumericArray2D<Complex> = TODO()

    override fun real(i0: Int): Double {
        requireValidIndices(i0)
        return array.real(offset + stride * i0)
    }

    override fun setReal(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setReal(value, offset + stride * i0)
    }

    override fun imag(i0: Int): Double {
        requireValidIndices(i0)
        return array.imag(offset + stride * i0)
    }

    override fun setImag(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setImag(value, offset + stride * i0)
    }

}