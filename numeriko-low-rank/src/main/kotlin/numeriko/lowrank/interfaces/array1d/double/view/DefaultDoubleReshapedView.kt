package numeriko.lowrank.interfaces.array1d.double.view

import tomasvolker.numeriko.core.config.NumerikoConfig
import numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.core.interfaces.arraynd.integer.MutableDoubleArrayND
import tomasvolker.core.interfaces.arraynd.integer.view.DefaultMutableDoubleArrayND
import tomasvolker.core.interfaces.factory.intArray1D
import tomasvolker.core.view.ElementOrder
import tomasvolker.core.view.linearIndex

class DefaultDoubleReshapedView(
        override val shape: IntArray1D,
        val array: MutableDoubleArray1D,
        order: ElementOrder = tomasvolker.numeriko.core.config.NumerikoConfig.defaultElementOrder,
        val offset: Int = 0,
        val strideArray: IntArray = order.strideArray(shape)
): DefaultMutableDoubleArrayND() {

    override val rank: Int
        get() = shape.size

    override val size: Int get() = array.size

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        array.setValue(convertIndices(indices), value)
    }

    override fun getView(vararg indices: IntProgression): MutableDoubleArrayND {
        require(indices.size == rank)
        for (axis in 0 until rank) {
            requireValidIndexRange(indices[axis], axis = axis)
        }
        return DefaultDoubleReshapedView(
                shape = intArray1D(rank) { axis -> indices[axis].count() },
                array = array,
                offset = convertIndices(IntArray(rank) { axis -> indices[axis].first }),
                strideArray = IntArray(rank) { axis -> indices[axis].step * strideArray[axis] }
        )
    }

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

}
