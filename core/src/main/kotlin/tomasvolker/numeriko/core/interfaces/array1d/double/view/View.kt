package tomasvolker.numeriko.core.interfaces.array1d.double.view

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D

class DefaultDoubleArray1DView(
        val array: DoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultDoubleArray1D() {

    override fun getDouble(index: Int): Double {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getDouble(offset + stride * index)
    }

}

open class DefaultMutableDoubleArray1DView (
        open val array: MutableDoubleArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableDoubleArray1D() {

    override fun setDouble(value: Double, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setDouble(value, offset + stride * index)
    }

    override fun getDouble(index: Int): Double {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getDouble(offset + stride * index)
    }

}
