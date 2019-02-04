package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultArray1DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultReshapedView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.defaultArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.defaultArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError2DMessage
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.view.ElementOrder

interface MutableArray1D<T>: Array1D<T>, MutableArrayND<T> {

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        return setValue(indices[0], value)
    }

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as0D(): Nothing = rankError(0)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

    override fun as1D() = this

    override fun withShape(shape0: Int, shape1: Int, order: ElementOrder): MutableArray2D<T> =
            withShape(intArray1DOf(shape0, shape1), order).as2D()

    override fun withShape(shape: IntArray1D, order: ElementOrder): MutableArrayND<T> =
            DefaultReshapedView(
                    shape = shape.copy(),
                    array = this,
                    offset = 0,
                    order = order
            )

    override fun lowerRank(axis: Int): MutableArray0D<T> {
        require(shape(axis) == 1)
        return defaultArray0DView(this, 0)
    }

    override fun higherRank(axis: Int): MutableArray2D<T> {
        require(axis in 0..1)
        return DefaultArray1DHigherRankView(this, axis)
    }

    fun setValue(i0: Int, value: T)
    fun setValue(i0: Index, value: T) = setValue(i0.computeValue(size), value)

    fun setValue(other: Array1D<T>) {
        requireSameSize(other, this)
        // Anti alias copy
        val copy = other.copy()

        forEachIndex { i0 ->
            setValue(i0, copy.getValue(i0))
        }

    }

    override fun getView(i0: Int  ): MutableArray0D<T> = defaultArray0DView(this, i0)
    override fun getView(i0: Index): MutableArray0D<T> = getView(i0.compute())

    override fun getView(i0: IntProgression): MutableArray1D<T> = defaultArray1DView(this, i0)
    override fun getView(i0: IndexProgression): MutableArray1D<T> = getView(i0.compute())

    fun setView(value: Array1D<T>, i0: IndexProgression): Unit = setView(value, i0.compute())
    fun setView(value: Array1D<T>, i0: IntProgression  ): Unit = getView(i0).setValue(value)

}

// Setter functions defined as extensions to avoid boxing when using get syntax on primitive specializations
operator fun <T> MutableArray1D<T>.set(i0: Int, value: T) = setValue(i0, value)
operator fun <T> MutableArray1D<T>.set(i0: Index, value: T) = setValue(i0, value)

operator fun <T> MutableArray1D<T>.set(i0: IntProgression, value: Array1D<T>) = setView(value, i0)
operator fun <T> MutableArray1D<T>.set(i0: IndexProgression, value: Array1D<T>) = setView(value, i0)
