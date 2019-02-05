package tomasvolker.numeriko.core.interfaces.arraynd.generic.view

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.view.ElementOrder

class DefaultArrayNDLinearView<T>(
        val array: MutableArrayND<T>,
        val order: ElementOrder = NumerikoConfig.defaultElementOrder
): DefaultMutableArray1D<T>() {

    override val size: Int = array.size

    private val strideArray: IntArray = order.strideArray(array.shape)

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return array.getValue(convertIndices(i0))
    }

    override fun setValue(i0: Int, value: T) {
        requireValidIndices(i0)
        array.setValue(convertIndices(i0), value)
    }

    fun convertIndices(index: Int): IntArray =
            order.linearToIndices(index, strideArray)

}