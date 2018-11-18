package tomasvolker.numeriko.complex.implementations.array1d

import tomasvolker.numeriko.complex.interfaces.array2d.view.DefaultMutableComplexArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

class NumerikoMutableComplexArray2D(
        override val real: MutableDoubleArray2D,
        override val imag: MutableDoubleArray2D
): DefaultMutableComplexArray2D() {

    init {
        require(real.shape == imag.shape)
    }

    override val shape0: Int
        get() = real.shape(0)

    override val shape1: Int
        get() = real.shape(1)

    override fun real(i0: Int, i1: Int): Double = real[i0, i1]
    override fun imag(i0: Int, i1: Int): Double = imag[i0, i1]

    override fun setReal(value: Double, i0: Int, i1: Int) {
        real[i0, i1] = value
    }

    override fun setImag(value: Double, i0: Int, i1: Int) {
        imag[i0, i1] = value
    }

}
