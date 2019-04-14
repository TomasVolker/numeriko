package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D

class DefaultComplexArray1DLowerRankView (
        val array: MutableComplexArray1D,
        val offset: Int
) : DefaultMutableComplexArray0D() {

    override fun higherRank(axis: Int): MutableNumericArray1D<Complex> = TODO("not implemented")

    override fun higherRank(): MutableNumericArray1D<Complex> = TODO()

    override fun real(): Double {
        return array.real(offset)
    }

    override fun setReal(value: Double) {
        array.setReal(value, offset)
    }

    override fun imag(): Double {
        return array.imag(offset)
    }

    override fun setImag(value: Double) {
        array.setImag(value, offset)
    }

}