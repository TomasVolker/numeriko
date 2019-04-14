package tomasvolker.numeriko.complex.implementations.array0d


import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D

class NumerikoComplexArray0D(
        var realValue: Double,
        var imagValue: Double
): DefaultMutableComplexArray0D() {

    override fun higherRank(): MutableNumericArray1D<Complex> {
        TODO("not implemented")
    }

    override fun higherRank(axis: Int): MutableNumericArray1D<Complex> {
        TODO("not implemented")
    }

    override fun real(): Double = realValue

    override fun imag(): Double = imagValue

    override fun setReal(value: Double) {
        realValue = value
    }

    override fun setImag(value: Double) {
        imagValue = value
    }

}