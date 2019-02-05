package tomasvolker.numeriko.core.implementations.numeriko.array1d.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D

class NumerikoArray1D<T>(
        val data: Array<T>
): DefaultMutableArray1D<T>() {

    override val size: Int get() = data.size

    override fun setValue(i0: Int, value: T) {
        data[i0] = value
    }

    override fun getValue(i0: Int): T = data[i0]

}