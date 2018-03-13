package tomasvolker.numeriko.core.interfaces.numeric.array1d

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.generic.array1d.ReadOnlyArray1D

interface ReadOnlyNumericArray1D<T: Number>: ReadOnlyArray1D<T> {

    override fun copy(): ReadOnlyNumericArray1D<T>

    override fun getView(i0: IntProgression): ReadOnlyNumericArray1D<T>

    override fun getView(i0: IndexProgression): ReadOnlyNumericArray1D<T>

    fun getInt(i0: Int): Int

    fun getInt(i0: Index): Int

    fun getDouble(i0: Int): Double

    fun getDouble(i0: Index): Double

}

