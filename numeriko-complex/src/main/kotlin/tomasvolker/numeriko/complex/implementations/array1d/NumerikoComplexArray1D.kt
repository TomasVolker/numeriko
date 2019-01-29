package tomasvolker.numeriko.complex.implementations.array1d

import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.numeric.MutableNumericArray2D

class NumerikoComplexArray1D(
        override val real: MutableDoubleArray1D,
        override val imag: MutableDoubleArray1D
): DefaultMutableComplexArray1D() {

    init {
        require(real.size == imag.size)
    }

    override val size: Int
        get() = real.size

    override fun real(i0: Int): Double = real[i0]
    override fun imag(i0: Int): Double = imag[i0]

    override fun setReal(value: Double, i0: Int) {
        real[i0] = value
    }

    override fun setImag(value: Double, i0: Int) {
        imag[i0] = value
    }

    override fun getView(i0: IntProgression): MutableComplexArray1D {
        requireValidIndexRange(i0)
        return NumerikoComplexArray1D(
                real = real[i0],
                imag = imag[i0]
        )
    }

    override fun higherRank(axis: Int): MutableNumericArray2D<Complex> {
        TODO("not implemented")
    }

    override fun setValue(i0: Int, value: Complex) {
        TODO("not implemented")
    }

}
