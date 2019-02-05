package tomasvolker.numeriko.core.interfaces.array2d.double.view

import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex0
import tomasvolker.numeriko.core.interfaces.array2d.generic.lastIndex1

class DefaultMutableDoubleArray2DView(
        val array: MutableDoubleArray2D,
        val offset0: Int,
        val offset1: Int,
        override val shape0: Int,
        override val shape1: Int,
        val stride0: Int,
        val stride1: Int
) : DefaultMutableDoubleArray2D() {

    init {
        array.requireValidIndices(convertIndex0(0), convertIndex1(0))
        array.requireValidIndices(convertIndex0(lastIndex0), convertIndex1(lastIndex1))
    }

    override fun get(i0: Int, i1: Int): Double {
        requireValidIndices(i0, i1)
        return array.getValue(
                convertIndex0(i0),
                convertIndex1(i1)
        )
    }

    override fun set(i0: Int, i1: Int, value: Double) {
        requireValidIndices(i0, i1)
        array.setValue(
                convertIndex0(i0),
                convertIndex1(i1),
                value
        )
    }

    fun convertIndex0(i0: Int): Int = offset0 + stride0 * i0
    fun convertIndex1(i1: Int): Int = offset1 + stride1 * i1

}

fun defaultDoubleArray2DView(array: MutableDoubleArray2D, i0: IntProgression, i1: IntProgression) =
        DefaultMutableDoubleArray2DView(
                array = array,
                offset0 = i0.first,
                offset1 = i1.first,
                shape0 = i0.count(),
                shape1 = i1.count(),
                stride0 = i0.step,
                stride1 = i1.step
        )