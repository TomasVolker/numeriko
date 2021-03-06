package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

interface MutableArray0D<T>: Array0D<T>, MutableArrayND<T> {

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        return setValue(value)
    }

    override fun setValue(value: T)

    fun setValue(other: Array0D<T>) {
        setValue(other.getValue())
    }

    override fun getView(): MutableArray0D<T> = this

    override fun lowerRank(axis: Int): Nothing {
        super<Array0D>.lowerRank(axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<Array0D>.arrayAlongAxis(axis, index)
    }

    override fun higherRank(): MutableArray1D<T> = higherRank(0)

    override fun higherRank(axis: Int): MutableArray1D<T> {
        require(axis == 0)
        return DefaultArray0DHigherRankView(this)
    }

}
