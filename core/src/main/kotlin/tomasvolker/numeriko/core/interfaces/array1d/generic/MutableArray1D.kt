package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultMutableArray1DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface MutableArray1D<T>: Array1D<T>, MutableArrayND<T> {

    override fun setValue(value: T, vararg indices: Int) {
        requireValidIndices(indices)
        return setValue(value, indices[0])
    }

    fun setValue(value: T, index: Int)

    fun setValue(value: T, index: Index) =
            setValue(value, index.computeValue(size))

    fun setValue(other: Array1D<T>) {

        require(other.size == this.size) {
            "Sizes must match"
        }

        for (i in indices) {
            setValue(other.getValue(i), i)
        }

    }

    fun setValue(value: T) {

        for (i in indices) {
            setValue(value, i)
        }

    }

    override fun getView(indexRange: IntProgression): MutableArray1D<T> =
            DefaultMutableArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableArray1D<T> =
            getView(indexRange.computeProgression(size))

    fun setView(value: Array1D<T>, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    fun setView(value: Array1D<T>, indexRange: IntProgression) =
            getView(indexRange).setValue(value.copy())

    fun setView(value: T, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    fun setView(value: T, indexRange: IntProgression) =
            getView(indexRange).setValue(value)

}

operator fun <T> MutableArray1D<T>.set(index: Int, value: T) = setValue(value, index)
operator fun <T> MutableArray1D<T>.set(index: Index, value: T) = setValue(value, index)

operator fun <T> MutableArray1D<T>.set(index: IntProgression, value: Array1D<T>) = setView(value, index)
operator fun <T> MutableArray1D<T>.set(index: IndexProgression, value: Array1D<T>) = setView(value, index)

operator fun <T> MutableArray1D<T>.set(index: IntProgression, value: T) = setView(value, index)
operator fun <T> MutableArray1D<T>.set(index: IndexProgression, value: T) = setView(value, index)
