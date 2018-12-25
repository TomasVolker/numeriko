package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.view.ElementOrder

class DefaultDoubleArrayNDLinearView(
        val array: MutableDoubleArrayND,
        val order: ElementOrder = NumerikoConfig.defaultElementOrder
): DefaultMutableDoubleArray1D() {

    override val size: Int = array.size

    private val strideArray: IntArray = order.strideArray(array.shape)

    override fun getDouble(i0: Int): Double {
        requireValidIndices(i0)
        return array.getValue(*convertIndices(i0))
    }

    override fun setDouble(value: Double, i0: Int) {
        requireValidIndices(i0)
        array.setValue(value, *convertIndices(i0))
    }

    fun convertIndices(index: Int): IntArray =
            order.linearToIndices(index, strideArray)

}