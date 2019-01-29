package tomasvolker.numeriko.core.implementations.numeriko.array2d.generic

import tomasvolker.numeriko.core.interfaces.array2d.generic.view.DefaultMutableArray2D

class NumerikoArray2D<T>(
        override val shape0: Int,
        override val shape1: Int,
        val data: Array<T>
): DefaultMutableArray2D<T>() {

    init {
        require(data.size == shape0 * shape1)
    }

    override val size: Int get() = data.size

    override fun setValue(i0: Int, i1: Int, value: T) {
        requireValidIndices(i0, i1)
        data[i0 * shape1 + i1] = value
    }

    override fun getValue(i0: Int, i1: Int): T {
        requireValidIndices(i0, i1)
        return data[i0 * shape1 + i1]
    }

}