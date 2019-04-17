package numeriko.lowrank.interfaces.array1d.generic.view

import numeriko.lowrank.interfaces.array1d.generic.MutableArray1D
import tomasvolker.core.interfaces.array2d.generic.view.DefaultMutableArray2D

class DefaultArray1DHigherRankView<T> (
        val array: MutableArray1D<T>,
        val axis: Int
) : DefaultMutableArray2D<T>() {

    init {
        require(axis <= 1)
    }

    override val shape0: Int
        get() = when(axis) {
            0 -> 1
            1 -> array.size
            else -> error("")
        }

    override val shape1: Int
        get() = when(axis) {
            0 -> array.size
            1 -> 1
            else -> error("")
        }

    override fun getValue(i0: Int, i1: Int): T {
        requireValidIndices(i0, i1)
        return when (axis) {
            0 -> array.getValue(i1)
            1 -> array.getValue(i0)
            else -> error("requireValidIndices should fail")
        }
    }

    override fun setValue(i0: Int, i1: Int, value: T) {
        requireValidIndices(i0, i1)
        when (axis) {
            0 -> array.setValue(i1, value)
            1 -> array.setValue(i0, value)
            else -> error("requireValidIndices should fail")
        }
    }

}
