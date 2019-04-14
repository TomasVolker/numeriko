package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.numeric.MutableNumericArray2D

class DefaultComplexArrayND1DView(
        val array: MutableComplexArrayND
) : DefaultMutableComplexArray1D() {

    init {
        require(array.rank == 1)
    }

    override val size: Int
        get() = array.shape(0)

    override fun real(i0: Int): Double = array.real(i0)
    override fun imag(i0: Int): Double = array.imag(i0)

    override fun setReal(value: Double, i0: Int) {
        array.setReal(value, i0)
    }

    override fun setImag(value: Double, i0: Int) {
        array.setImag(value, i0)
    }

    override fun higherRank(axis: Int): MutableNumericArray2D<Complex> = TODO("not implemented")

}
