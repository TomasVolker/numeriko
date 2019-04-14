package tomasvolker.numeriko.lowrank.interfaces.array1d.double.view

import tomasvolker.numeriko.lowrank.interfaces.array0d.double.DefaultMutableDoubleArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D

class DefaultDoubleArray0DView (
        val array: MutableDoubleArray1D,
        val offset: Int
) : DefaultMutableDoubleArray0D() {

    init {
        array.requireValidIndices(offset)
    }

    override fun set(value: Double) {
        array.setDouble(offset, value)
    }

    override fun get(): Double {
        return array.getDouble(offset)
    }

}

fun defaultDoubleArray0DView(array: MutableDoubleArray1D, i0: Int) =
        DefaultDoubleArray0DView(
                array = array,
                offset = i0
        )
