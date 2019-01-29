package tomasvolker.numeriko.core.implementations.numeriko.arraynd

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultMutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.view.ElementOrder
import tomasvolker.numeriko.core.view.linearIndex

class NumerikoArrayND<T>(
        override val shape: IntArray1D,
        val data: Array<T>,
        order: ElementOrder = NumerikoConfig.defaultElementOrder,
        val offset: Int = 0,
        val strideArray: IntArray = order.strideArray(shape)
): DefaultMutableArrayND<T>() {

    override val rank: Int
        get() = shape.size

    override val size: Int get() = data.size

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return data[convertIndices(indices)]
    }

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        data[convertIndices(indices)] = value
    }

    override fun getView(vararg indices: IntProgression): MutableArrayND<T> {
        require(indices.size == rank)
        for (axis in 0 until rank) {
            requireValidIndexRange(indices[axis], axis = axis)
        }

        return NumerikoArrayND(
                shape = intArray1D(rank) { axis -> indices[axis].count() },
                data = data,
                offset = convertIndices(IntArray(rank) { axis -> indices[axis].first }),
                strideArray = IntArray(rank) { axis -> indices[axis].step * strideArray[axis] }
        )
    }

    private fun convertIndices(indices: IntArray): Int =
            linearIndex(offset, strideArray, indices)

}

