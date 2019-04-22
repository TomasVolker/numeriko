package numeriko.complex.implementations.arraynd

import numeriko.complex.interfaces.arraynd.view.DefaultMutableComplexArrayND
import numeriko.complex.primitives.Complex
import numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.core.interfaces.arraynd.integer.MutableDoubleArrayND
import tomasvolker.core.interfaces.arraynd.numeric.MutableNumericArrayND

class NumerikoComplexArrayND(
        val real: MutableDoubleArrayND,
        val imag: MutableDoubleArrayND
): DefaultMutableComplexArrayND() {

    override fun higherRank(axis: Int): MutableNumericArrayND<Complex> {
        TODO("not implemented")
    }

    override fun setValue(indices: IntArray, value: Complex) {
        TODO("not implemented")
    }

    init {
        require(real.shape == imag.shape)
    }

    override val shape: IntArray1D
        get() = real.shape

    override fun real(vararg indices: Int): Double = real.getDouble(indices)

    override fun imag(vararg indices: Int): Double = imag.getDouble(indices)

    override fun setReal(value: Double, vararg indices: Int) {
        real.setDouble(indices, value)
    }

    override fun setImag(value: Double, vararg indices: Int) {
        imag.setDouble(indices, value)
    }

}