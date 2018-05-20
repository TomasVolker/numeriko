package tomasvolker.numeriko.legacy.core.interfaces.generic.array1d

import tomasvolker.numeriko.legacy.core.index.Index
import tomasvolker.numeriko.legacy.core.index.IndexProgression
import tomasvolker.numeriko.legacy.core.interfaces.generic.arraynd.ArrayND
import tomasvolker.numeriko.legacy.core.interfaces.integer.array1d.ReadOnlyIntArray1D

interface Array1D<T>: ReadOnlyArray1D<T>, ArrayND<T> {

    fun setValue(value: T, i0: Int)

    fun setValue(value: T, i0: Index) =
            setValue(value, i0.computeValue(size))

    fun setValue(value: ReadOnlyArray1D<T>, indices: IntProgression)

    fun setValue(value: ReadOnlyArray1D<T>, indices: IndexProgression) =
            setValue(value, indices.computeProgression(size))

    fun setAll(setter: (i0: Int) -> T)


}