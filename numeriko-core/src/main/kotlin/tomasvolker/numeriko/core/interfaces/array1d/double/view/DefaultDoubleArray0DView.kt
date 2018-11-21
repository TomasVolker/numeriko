package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array0d.double.DefaultMutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex

class DefaultDoubleArray0DView (
        val array: MutableDoubleArray1D,
        val offset: Int
) : DefaultMutableDoubleArray0D() {

    init {
        array.requireValidIndices(offset)
    }

    override fun setDouble(value: Double) {
        array.setDouble(value, offset)
    }

    override fun getDouble(): Double {
        return array.getDouble(offset)
    }

}

fun defaultDoubleArray0DView(array: MutableDoubleArray1D, i0: Int) =
        DefaultDoubleArray0DView(
                array = array,
                offset = i0
        )
