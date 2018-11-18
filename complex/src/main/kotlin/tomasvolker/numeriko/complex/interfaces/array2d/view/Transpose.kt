package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D

class DefaultMutableComplexArray2DTransposeView(
        val array: MutableComplexArray2D
) : DefaultMutableComplexArray2D() {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override fun real(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.real(i1, i0)
    }

    override fun imag(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.imag(i1, i0)
    }

    override fun setReal(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setReal(value, i1, i0)
    }

    override fun setImag(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setImag(value, i1, i0)
    }

    override fun transpose(): MutableComplexArray2D = array

}

