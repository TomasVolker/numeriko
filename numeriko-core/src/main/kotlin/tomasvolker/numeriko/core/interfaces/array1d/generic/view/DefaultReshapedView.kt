package tomasvolker.numeriko.core.interfaces.array1d.generic.view

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.linearIndex

class DefaultReshapedView<T>(
        override val shape: IntArray1D,
        val array: MutableArray1D<T>,
        order: ElementOrder = NumerikoConfig.defaultElementOrder,
        val offset: Int = 0,
        val strideArray: IntArray = order.strideArray(shape)
): DefaultMutableArrayND<T>() {

    override val rank: Int
        get() = shape.size

    override val size: Int get() = array.size

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return array.getValue(convertIndices(indices))
    }

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        array.setValue(value, convertIndices(indices))
    }

    override fun getView(vararg indices: IntProgression): MutableArrayND<T> {
        require(indices.size == rank)
        for (axis in 0 until rank) {
            requireValidIndexRange(indices[axis], axis = axis)
        }
        return DefaultReshapedView(
                shape = intArray1D(rank) { axis -> indices[axis].count() },
                array = array,
                offset = convertIndices(IntArray(rank) { axis -> indices[axis].first }),
                strideArray = IntArray(rank) { axis -> indices[axis].step * strideArray[axis] }
        )
    }

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

}
