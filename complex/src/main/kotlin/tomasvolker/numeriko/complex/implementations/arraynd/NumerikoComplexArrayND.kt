package tomasvolker.numeriko.complex.implementations.arraynd

import tomasvolker.numeriko.complex.interfaces.arraynd.view.DefaultMutableComplexArrayND
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

class NumerikoComplexArrayND(
        val real: MutableDoubleArrayND,
        val imag: MutableDoubleArrayND
): DefaultMutableComplexArrayND() {

    init {
        require(real.shape == imag.shape)
    }

    override val shape: IntArray1D
        get() = real.shape

    override fun real(vararg indices: Int): Double = real.getDouble(*indices)

    override fun imag(vararg indices: Int): Double = imag.getDouble(*indices)

    override fun setReal(value: Double, vararg indices: Int) {
        real.setDouble(value, *indices)
    }

    override fun setImag(value: Double, vararg indices: Int) {
        imag.setDouble(value, *indices)
    }

}