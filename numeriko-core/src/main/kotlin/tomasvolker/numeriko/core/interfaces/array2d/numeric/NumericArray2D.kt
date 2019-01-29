package tomasvolker.numeriko.core.interfaces.array2d.numeric

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array0d.numeric.NumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.NumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultArrayNDIterator
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D

interface NumericArray2D<out N: Number>: Array2D<N>, NumericArrayND<N> {

    override fun getValue(indices: IntArray): N {
        requireValidIndices(indices)
        return getValue(indices[0], indices[1])
    }

    fun getDouble(i0: Int, i1: Int): Double = getValue(i0, i1).toDouble()
    fun getFloat (i0: Int, i1: Int): Float  = getValue(i0, i1).toFloat()
    fun getLong  (i0: Int, i1: Int): Long   = getValue(i0, i1).toLong()
    fun getInt   (i0: Int, i1: Int): Int    = getValue(i0, i1).toInt()
    fun getShort (i0: Int, i1: Int): Short  = getValue(i0, i1).toShort()


    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return getDouble(indices[0], indices[1])
    }

    override fun getFloat(indices: IntArray): Float {
        requireValidIndices(indices)
        return getFloat(indices[0], indices[1])
    }

    override fun getLong(indices: IntArray): Long {
        requireValidIndices(indices)
        return getLong(indices[0], indices[1])
    }

    override fun getInt(indices: IntArray): Int {
        requireValidIndices(indices)
        return getInt(indices[0], indices[1])
    }

    override fun getShort(indices: IntArray): Short {
        requireValidIndices(indices)
        return getShort(indices[0], indices[1])
    }

    override fun toDoubleArrayND(): DoubleArray2D = doubleArray2D(shape0, shape1) { i0, i1 -> this.getDouble(i0, i1) }

    override fun getView(i0: Int, i1: Int): NumericArray0D<N>

    override fun getView(i0: Int, i1: IntProgression): NumericArray1D<N>
    override fun getView(i0: IntProgression, i1: Int): NumericArray1D<N>

    override fun getView(i0: IntProgression, i1: IntProgression): NumericArray2D<N>

    override fun getView(vararg indices: IntProgression): NumericArray2D<N> {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(vararg indices: IndexProgression): NumericArray2D<N> =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): NumericArray1D<N>

    override fun arrayAlongAxis(axis: Int, index: Int): NumericArray1D<N>

    override fun copy(): NumericArray2D<N>

    override fun asMutable(): MutableNumericArray2D<@UnsafeVariance N> = this as MutableNumericArray2D

    override fun iterator(): Iterator<N> = DefaultArrayNDIterator(this)

}
