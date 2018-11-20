package tomasvolker.numeriko.complex.implementations.array2d

import tomasvolker.numeriko.complex.implementations.array1d.NumerikoComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.complex.interfaces.array2d.view.DefaultMutableComplexArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D

class NumerikoComplexArray2D(
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

    override fun getView(i0: Int, i1: IntProgression): MutableComplexArray1D {
        requireValidIndex(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoComplexArray1D(
                real = real[i0, i1],
                imag = imag[i0, i1]
        )
    }

    override fun getView(i0: IntProgression, i1: Int): MutableComplexArray1D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndex(i1, axis = 1)
        return NumerikoComplexArray1D(
                real = real[i0, i1],
                imag = imag[i0, i1]
        )
    }

    override fun getView(i0: IntProgression, i1: IntProgression): MutableComplexArray2D {
        requireValidIndexRange(i0, axis = 0)
        requireValidIndexRange(i1, axis = 1)
        return NumerikoComplexArray2D(
                real = real[i0, i1],
                imag = imag[i0, i1]
        )
    }

    override fun lowerRank(axis: Int): MutableComplexArray1D {
        requireValidAxis(axis)
        return NumerikoComplexArray1D(
                real = real.lowerRank(axis),
                imag = imag.lowerRank(axis)
        )
    }

}
