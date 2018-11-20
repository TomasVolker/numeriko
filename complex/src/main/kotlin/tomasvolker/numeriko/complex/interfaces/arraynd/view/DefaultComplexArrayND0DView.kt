package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND

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

}
