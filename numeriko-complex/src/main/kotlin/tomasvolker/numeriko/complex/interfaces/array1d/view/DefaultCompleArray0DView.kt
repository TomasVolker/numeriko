package tomasvolker.numeriko.complex.interfaces.array1d.view

import tomasvolker.numeriko.complex.interfaces.array0d.DefaultMutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.MutableComplexArray1D

class DefaultComplexArray0DView (
        val array: MutableComplexArray1D,
        val offset: Int
) : DefaultMutableComplexArray0D() {

    init {
        array.requireValidIndices(offset)
    }

    override fun real(): Double = array.real(offset)

    override fun imag(): Double = array.imag(offset)

    override fun setReal(value: Double) {
        array.setReal(value, offset)
    }

    override fun setImag(value: Double) {
        array.setImag(value, offset)
    }

    init {
        array.requireValidIndices(offset)
    }

}

fun defaultComplexArray0DView(array: MutableComplexArray1D, i0: Int) =
        DefaultComplexArray0DView(
                array = array,
                offset = i0
        )
