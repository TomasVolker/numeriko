package tomasvolker.numeriko.core.interfaces.array0d.numeric

import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array0d.generic.DefaultArray0DIterator
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.NumericArrayND

interface NumericArray0D<out N: Number>: Array0D<N>, NumericArrayND<N> {

    override fun getValue(vararg indices: Int): N = getValue()

    fun getDouble(): Double = getValue().toDouble()
    fun getFloat (): Float  = getValue().toFloat()
    fun getLong  (): Long   = getValue().toLong()
    fun getInt   (): Int    = getValue().toInt()
    fun getShort (): Short  = getValue().toShort()

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble()
    }

    override fun getFloat(vararg indices: Int): Float {
        requireValidIndices(indices)
        return getFloat()
    }

    override fun getLong(vararg indices: Int): Long {
        requireValidIndices(indices)
        return getLong()
    }

    override fun getInt(vararg indices: Int): Int {
        requireValidIndices(indices)
        return getInt()
    }

    override fun getShort(vararg indices: Int): Short {
        requireValidIndices(indices)
        return getShort()
    }

    override fun getView(): NumericArray0D<N> = this

    override fun lowerRank(axis: Int): Nothing {
        super.lowerRank(axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<Array0D>.arrayAlongAxis(axis, index)
    }

    override fun copy(): NumericArray0D<N> = TODO() // copy(this)

    override fun asMutable(): MutableNumericArray0D<@UnsafeVariance N> = this as MutableNumericArray0D<N>

    override fun iterator(): Iterator<N> = DefaultArray0DIterator(this)

}
