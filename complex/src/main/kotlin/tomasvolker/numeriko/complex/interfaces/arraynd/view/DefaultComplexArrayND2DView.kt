package tomasvolker.numeriko.complex.interfaces.arraynd.view

import tomasvolker.numeriko.complex.interfaces.array2d.view.DefaultMutableComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.MutableComplexArrayND

class DefaultComplexArrayND2DView(
        val array: MutableComplexArrayND
) : DefaultMutableComplexArray2D() {

    init {
        require(array.rank == 2)
    }

    override val shape0: Int
        get() = array.shape(0)

    override val shape1: Int
        get() = array.shape(0)

    override fun real(i0: Int, i1: Int): Double = array.real(i0, i1)

    override fun imag(i0: Int, i1: Int): Double = array.imag(i0, i1)

    override fun setReal(value: Double, i0: Int, i1: Int) {
        array.setReal(value, i0, i1)
    }

    override fun setImag(value: Double, i0: Int, i1: Int) {
        array.setImag(value, i0, i1)
    }

}
