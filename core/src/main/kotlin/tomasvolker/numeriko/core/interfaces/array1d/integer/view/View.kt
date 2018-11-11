package tomasvolker.numeriko.core.interfaces.array1d.integer.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D

open class DefaultMutableIntArray1DView (
        open val array: MutableIntArray1D,
        val offset: Int,
        override val size: Int,
        val stride: Int
) : DefaultMutableIntArray1D() {

    override fun setInt(value: Int, index: Int) {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        array.setInt(value, offset + stride * index)
    }

    override fun getInt(index: Int): Int {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException(index)
        }

        return array.getInt(offset + stride * index)
    }

}
