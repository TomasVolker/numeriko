package tomasvolker.numeriko.core.interfaces.array1d.integer.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D

class DefaultMutableIntArray1DView (
        val array: MutableIntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableIntArray1D() {

    init {
        array.requireValidIndices(convertIndex(0))
        array.requireValidIndices(convertIndex(lastIndex))
    }

    override fun setInt(i0: Int, value: Int) {
        requireValidIndices(i0)
        array.setInt(offset + stride * i0, value)
    }

    override fun getInt(i0: Int): Int {
        requireValidIndices(i0)
        return array.getInt(offset + stride * i0)
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

}

fun defaultIntArray1DView(array: MutableIntArray1D, i0: IntProgression) =
        DefaultMutableIntArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
