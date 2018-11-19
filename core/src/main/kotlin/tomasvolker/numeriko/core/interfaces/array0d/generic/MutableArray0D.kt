package tomasvolker.numeriko.core.interfaces.array0d.generic

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

}
