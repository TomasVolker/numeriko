package tomasvolker.numeriko.core.interfaces.numeric.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D

interface NumericArray1D<T: Number>: ReadOnlyNumericArray1D<T>, Array1D<T> {

    override fun copy(): NumericArray1D<T>

    override fun getView(i0: IntProgression): NumericArray1D<T>

    override fun getView(i0: IndexProgression): NumericArray1D<T>

    fun setInt(value: Int, i0: Int)

    fun setInt(value: Int, i0: Index)

    fun setDouble(value: Double, i0: Int)

    fun setDouble(value: Double, i0: Index)

}