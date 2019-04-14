package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.view.without

class DefaultDoubleArrayNDHigherRankView(
        val array: MutableDoubleArrayND,
        val axis: Int
) : DefaultMutableDoubleArrayND() {

    init {
        require(axis <= array.rank)
    }

    override val shape: IntArray1D = array.shape.inject(index = axis, value = 1)

    private fun convertIndices(indices: IntArray): IntArray =
            indices.without(index = axis)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.get(*convertIndices(indices))
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        array.setDouble(convertIndices(indices), value)
    }

}