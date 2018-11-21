package tomasvolker.numeriko.core.interfaces.array1d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.*

class DefaultArray1DView<T>(
        val array: MutableArray1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableArray1D<T>() {

    init {
        array.requireValidIndices(convertIndex(0))
        array.requireValidIndices(convertIndex(lastIndex))
    }

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return array.getValue(offset + stride * i0)
    }

    override fun setValue(value: T, i0: Int) {
        requireValidIndices(i0)
        array.setValue(value, offset + stride * i0)
    }

    fun convertIndex(i0: Int): Int = offset + stride * i0

}

fun <T> defaultArray1DView(array: MutableArray1D<T>, i0: IntProgression) =
        DefaultArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
