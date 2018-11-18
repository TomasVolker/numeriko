package tomasvolker.numeriko.complex.implementations.array1d

import tomasvolker.numeriko.complex.interfaces.array1d.view.DefaultMutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

class NumerikoMutableComplexArray1D(
        val values: MutableDoubleArray2D
): DefaultMutableComplexArray1D() {

    override val size: Int
        get() = values.shape0

    override val real: DoubleArray1D
        get() = values[All, 0]

    override val imag: DoubleArray1D
        get() = values[All, 1]

    override fun real(i0: Int): Double = values[i0, 0]
    override fun imag(i0: Int): Double = values[i0, 1]

    override fun setReal(value: Double, i0: Int) {
        values[i0, 0] = value
    }

    override fun setImag(value: Double, i0: Int) {
        values[i0, 1] = value
    }

    override fun getView(i0: IntProgression): MutableComplexArray1D {
        requireValidIndexRange(i0)
        return NumerikoMutableComplexArray1D(
                values = values[i0, All]
        )
    }

}
