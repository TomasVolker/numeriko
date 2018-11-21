package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.defaultArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.defaultArray1DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.requireSameSize

interface MutableArray1D<T>: Array1D<T>, MutableArrayND<T> {

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        return setValue(value, indices[0])
    }

    override fun lowerRank(axis: Int): MutableArray0D<T> {
        require(shape(axis) == 1)
        return defaultArray0DView(this, 0)
    }

    fun setValue(value: T, i0: Int)

    fun setValue(value: T, i0: Index) =
            setValue(value, i0.computeValue(size))

    fun setValue(other: Array1D<T>) {
        requireSameSize(other, this)
        // Anti alias copy
        val copy = other.copy()
        for (i in indices) {
            setValue(copy.getValue(i), i)
        }

    }

    override fun setValue(value: T) {

        for (i in indices) {
            setValue(value, i)
        }

    }

    override fun getView(i0: Int  ): MutableArray0D<T> = defaultArray0DView(this, i0)
    override fun getView(i0: Index): MutableArray0D<T> = getView(i0.compute())

    override fun getView(i0: IntProgression): MutableArray1D<T> = defaultArray1DView(this, i0)
    override fun getView(i0: IndexProgression): MutableArray1D<T> = getView(i0.compute())

    fun setView(value: Array1D<T>, i0: IndexProgression): Unit = setView(value, i0.compute())
    fun setView(value: Array1D<T>, i0: IntProgression  ): Unit = getView(i0).setValue(value)

    fun setView(value: T, i0: IndexProgression): Unit = setView(value, i0.compute())
    fun setView(value: T, i0: IntProgression  ): Unit = getView(i0).setValue(value)

}

// Setter functions defined as extensions to avoid boxing when using get syntax on primitive specializations
operator fun <T> MutableArray1D<T>.set(i0: Int, value: T) = setValue(value, i0)
operator fun <T> MutableArray1D<T>.set(i0: Index, value: T) = setValue(value, i0)

operator fun <T> MutableArray1D<T>.set(i0: IntProgression, value: Array1D<T>) = setView(value, i0)
operator fun <T> MutableArray1D<T>.set(i0: IndexProgression, value: Array1D<T>) = setView(value, i0)

operator fun <T> MutableArray1D<T>.set(i0: IntProgression, value: T) = setView(value, i0)
operator fun <T> MutableArray1D<T>.set(i0: IndexProgression, value: T) = setView(value, i0)
