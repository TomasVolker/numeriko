package tomasvolker.numeriko.core.implementations.numeriko.array1d.integer

import tomasvolker.numeriko.core.interfaces.array1d.integer.view.DefaultMutableIntArray1D

class NumerikoMutableIntArray1D(
        val data: IntArray
): DefaultMutableIntArray1D() {

    override val size: Int get() = data.size

    override fun setInt(value: Int, i0: Int) {
        data[i0] = value
    }

    override fun getInt(i0: Int): Int = data[i0]

}