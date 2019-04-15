package tomasvolker.numeriko.lowrank.interfaces.array1d.numeric

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.numeric.NumericArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.DefaultArray1DIterator
import tomasvolker.numeriko.core.interfaces.array2d.numeric.NumericArray2D
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

interface NumericArray1D<out N: Number>: Array1D<N>, NumericArrayND<N> {

    override fun getValue(indices: IntArray): N {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

    fun getDouble(i0: Int): Double = getValue(i0).toDouble()
    fun getFloat (i0: Int): Float  = getValue(i0).toFloat()
    fun getLong  (i0: Int): Long   = getValue(i0).toLong()
    fun getInt   (i0: Int): Int    = getValue(i0).toInt()
    fun getShort (i0: Int): Short  = getValue(i0).toShort()

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return getDouble(indices[0])
    }

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return getFloat(indices[0])
    }

    override fun getLong(indices: IntArray): Long {
        requireValidIndices(indices)
        return getLong(indices[0])
    }

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return getInt(indices[0])
    }

    override fun getShort(indices: IntArray): Short {
        requireValidIndices(indices)
        return getShort(indices[0])
    }

    override fun lowerRank(axis: Int): NumericArray0D<N>

    override fun higherRank(axis: Int): NumericArray2D<N>

    override fun getView(vararg indices: IntProgression): NumericArray1D<N> {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(i0: Int): NumericArray0D<N>
    override fun getView(i0: IntProgression): NumericArray1D<N>

    override fun getView(i0: Index): NumericArray0D<N> = getView(i0.compute())
    override fun getView(i0: IndexProgression): NumericArray1D<N> = getView(i0.compute())

    override fun copy(): NumericArray1D<N>

    override fun arrayAlongAxis(axis: Int, index: Int): NumericArray0D<N>

    override fun asMutable(): MutableNumericArray1D<@UnsafeVariance N> = this as MutableNumericArray1D<N>

    override fun iterator(): Iterator<N> = DefaultArray1DIterator(this)

}
