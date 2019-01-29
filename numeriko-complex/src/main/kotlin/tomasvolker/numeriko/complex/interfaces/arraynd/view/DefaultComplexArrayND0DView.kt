package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.core.interfaces.array1d.numeric.MutableNumericArray1D

class DefaultComplexArrayND0DView(
        val array: MutableComplexArrayND
) : DefaultMutableComplexArray0D() {

    init {
        require(array.rank == 0)
    }
    override val size: Int
        get() = 1

    override fun real(): Double = array.real()

    override fun imag(): Double = array.imag()

    override fun setReal(value: Double) {
        array.setReal(value)
    }
    override fun setImag(value: Double) {
        array.setImag(value)
    }

    override fun higherRank(): MutableNumericArray1D<Complex> = TODO("not implemented")
    override fun higherRank(axis: Int): MutableNumericArray1D<Complex> = TODO("not implemented")

}
