package tomasvolker.numeriko.core.implementations.numeriko.array1d.double

import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1D

class NumerikoMutableDoubleArray1D(
        val data: DoubleArray
): DefaultMutableDoubleArray1D() {

    override val size: Int get() = data.size

    override fun setDouble(value: Double, index: Int) {
        data[index] = value
    }

    override fun getDouble(index: Int): Double = data[index]

}