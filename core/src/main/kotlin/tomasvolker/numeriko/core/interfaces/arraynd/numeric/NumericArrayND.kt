package tomasvolker.numeriko.core.interfaces.arraynd.numeric

import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultArrayNDIterator
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND

interface NumericArrayND<out N: Number>: ArrayND<N> {

    override fun getValue(vararg indices: Int): N

    fun cast(value: Number): N

    fun getDouble(vararg indices: Int): Double = getValue(*indices).toDouble()
    fun getDouble(indices: IntArray1D): Double = getDouble(*indices.toIntArray())

    fun getFloat(vararg indices: Int): Float = getValue(*indices).toFloat()
    fun getFloat(indices: IntArray1D): Float = getFloat(*indices.toIntArray())

    fun getLong(vararg indices: Int): Long = getValue(*indices).toLong()
    fun getLong(indices: IntArray1D): Long = getLong(*indices.toIntArray())

    fun getInt(vararg indices: Int): Int = getValue(*indices).toInt()
    fun getInt(indices: IntArray1D): Int = getInt(*indices.toIntArray())

    fun getShort(vararg indices: Int): Short = getValue(*indices).toShort()
    fun getShort(indices: IntArray1D): Short = getShort(*indices.toIntArray())

    fun toDoubleArrayND(): DoubleArrayND = doubleArrayND(shape) { indices -> this.getDouble(indices) }

    override fun getView(vararg indices: IntProgression): NumericArrayND<N>

    override fun getView(vararg indices: IndexProgression): NumericArrayND<N> =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): NumericArrayND<N>

    override fun arrayAlongAxis(axis: Int, index: Int): NumericArrayND<N> =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    override fun copy(): NumericArrayND<N>

    override fun asMutable(): MutableNumericArrayND<@UnsafeVariance N> = this as MutableNumericArrayND

    override fun iterator(): Iterator<N> = DefaultArrayNDIterator(this)

}
