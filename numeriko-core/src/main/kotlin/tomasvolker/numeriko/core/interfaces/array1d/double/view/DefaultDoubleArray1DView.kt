package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex

class DefaultDoubleArray1DView (
        val array: MutableDoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableDoubleArray1D() {

    init {
        array.requireValidIndices(convertIndex(0))
        array.requireValidIndices(convertIndex(lastIndex))
    }

    override operator fun get(i0: Int): Double {
        requireValidIndices(i0)
        return array.getDouble(convertIndex(i0))
    }

    override operator fun set(i0: Int, value: Double) {
        requireValidIndices(i0)
        array.setDouble(convertIndex(i0), value)
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

}

fun defaultDoubleArray1DView(array: MutableDoubleArray1D, i0: IntProgression) =
        DefaultDoubleArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
