package tomasvolker.numeriko.core.interfaces.generic.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.int.array1d.ReadOnlyIntArray1D

interface Array1D<T>: ReadOnlyArray1D<T> {

    /*
    override fun linearCursor(): NDArrayLinearCursor<T>

    override fun cursor(): NDArrayCursor<T>
*/

    fun setValue(value: T, i0: Int)

    fun setValue(value: T, i0: Index)

    fun setValue(value: T, indexArray: ReadOnlyIntArray1D)

    fun setValue(value: ReadOnlyArray1D<T>, indices: IntProgression)

    fun setValue(value: ReadOnlyArray1D<T>, indices: IndexProgression)

    fun setAll(setter: (indexArray: Int) -> T)

    override fun getDataAsArray(): Array<T>

    override fun unsafeGetDataAsArray(): Array<T>

}