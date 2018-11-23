package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.operations.inject
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.view.with
import tomasvolker.numeriko.core.view.without

class DefaultArrayNDHigherRankView<T>(
        val array: MutableArrayND<T>,
        val axis: Int
) : DefaultMutableArrayND<T>() {

    init {
        require(array.shape(axis) <= 1)
    }

    override val shape: IntArray1D = array.shape.inject(index = axis, value = 1)

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return array.getValue(*convertIndices(indices))
    }

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, *convertIndices(indices))
    }

    fun convertIndices(indices: IntArray): IntArray =
            indices.without(index = axis)

}

