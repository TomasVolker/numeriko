package tomasvolker.numeriko.core.interfaces.array1d.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.generic.*

open class DefaultArray1DView<out T>(
        open val array: Array1D<T>,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultArray1D<T>() {

    override fun getValue(index: Int): T {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getValue(offset + stride * index)
    }

}

class DefaultMutableArray1DView<T>(
        override val array: MutableArray1D<T>,
        offset: Int,
        size: Int,
        stride: Int
) : DefaultArray1DView<T>(
        array,
        offset,
        size,
        stride
), MutableArray1D<T> {

    override fun setValue(value: T, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setValue(value, offset + stride * index)
    }

}
