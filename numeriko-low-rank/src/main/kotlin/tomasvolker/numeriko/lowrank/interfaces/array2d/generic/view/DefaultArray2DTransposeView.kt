package tomasvolker.numeriko.lowrank.interfaces.array2d.generic.view

import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.*

class DefaultArray2DTransposeView<T>(
        val array: MutableArray2D<T>
) : DefaultMutableArray2D<T>() {

    override val shape0: Int
        get() = array.shape1

    override val shape1: Int
        get() = array.shape0

    override fun getValue(i0: Int, i1: Int): T {
        requireValidIndices(i0, i1)
        return array.getValue(i1, i0)
    }

    override fun setValue(i0: Int, i1: Int, value: T) {
        requireValidIndices(i0, i1)
        return array.setValue(i1, i0, value)
    }

}


