package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.view.with

class DefaultDoubleArrayNDHigherRankView(
        val array: MutableDoubleArrayND,
        val axis: Int
) : DefaultMutableDoubleArrayND() {

    init {
        require(axis <= array.rank)
    }

    override val shape: IntArray1D = array.shape.remove(axis)

    private fun convertIndices(indices: IntArray): IntArray =
            indices.with(value = 0, index = axis)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getDouble(*convertIndices(indices))
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        array.setDouble(value,*convertIndices(indices))
    }

}