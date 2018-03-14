package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayND
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayNDCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ArrayNDLinearCursor
import tomasvolker.numeriko.core.interfaces.generic.arraynd.ReadOnlyArrayND

interface Array1D<T>: ReadOnlyArray1D<T>, ArrayND<T> {

    fun setValue(value: T, i0: Int)

    fun setValue(value: T, i0: Index)

    fun setValue(value: ReadOnlyArray1D<T>, indices: IntProgression)

    fun setValue(value: ReadOnlyArray1D<T>, indices: IndexProgression)

    fun setAll(setter: (i0: Int) -> T)

    override fun getView(vararg indices: Any): Array1D<T>

}