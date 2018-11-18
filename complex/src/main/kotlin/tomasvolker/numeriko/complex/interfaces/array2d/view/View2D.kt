package tomasvolker.numeriko.complex.interfaces.array2d.view

import tomasvolker.numeriko.complex.interfaces.array2d.MutableComplexArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex0
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex1

class DefaultMutableComplexArray2DView(
        val array: MutableComplexArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DefaultMutableComplexArray2D() {

    init {
        array.requireValidIndices(convertIndex0(0), convertIndex1(0))
        array.requireValidIndices(convertIndex0(lastIndex0), convertIndex1(lastIndex1))
    }

    override fun real(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.real(
                convertIndex0(i0),
                convertIndex1(i1)
        )
    }

    override fun imag(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.imag(
                convertIndex0(i0),
                convertIndex1(i1)
        )
    }

    override fun setReal(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setReal(
                value,
                convertIndex0(i0),
                convertIndex1(i1)
        )
    }

    override fun setImag(value: Double, i0: Int, i1: Int) {
        requireValidIndices(i0, i1)
        array.setImag(
                value,
                convertIndex0(i0),
                convertIndex1(i1)
        )
    }

    fun convertIndex0(i0: Int): Int = offset0 + stride0 * i0
    fun convertIndex1(i1: Int): Int = offset1 + stride1 * i1

}

fun defaultComplexArray2DView(array: MutableComplexArray2D, i0: IntProgression, i1: IntProgression) =
        DefaultMutableComplexArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1.first,
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = i0.step,
                stride1 = i1.step
        )