package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1D

class DefaultArray0DHigherRankView<T> (
        val array: MutableArray0D<T>
) : DefaultMutableArray1D<T>() {

    override val size: Int get() = 1

    override fun getValue(i0: Int): T {
        requireValidIndices(i0)
        return array.getValue()
    }

    override fun setValue(value: T, i0: Int) {
        requireValidIndices(i0)
        setValue(value)
    }

}
