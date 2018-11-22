package tomasvolker.numeriko.core.interfaces.arraynd.numeric

import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeForEachIndices
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableNumericArrayND<N: Number>: NumericArrayND<N>, MutableArrayND<N> {

    override fun getView(vararg indices: IntProgression): MutableNumericArrayND<N>

    override fun getView(vararg indices: IndexProgression): MutableNumericArrayND<N> =
            getView(*indices.computeIndices())

    fun setDouble(value: Double, vararg indices: Int) = setValue(cast(value), *indices)
    fun setDouble(value: Double, indices: IntArray1D) = setDouble(value, *indices.toIntArray())

    fun setFloat(value: Float, vararg indices: Int) = setValue(cast(value))
    fun setFloat(value: Float, indices: IntArray1D) = setFloat(value, *indices.toIntArray())

    fun setLong(value: Long, vararg indices: Int) = setValue(cast(value), *indices)
    fun setLong(value: Long, indices: IntArray1D) = setLong(value, *indices.toIntArray())

    fun setInt(value: Int, vararg indices: Int) = setValue(cast(value))
    fun setInt(value: Int, indices: IntArray1D) = setInt(value, *indices.toIntArray())

    fun setShort(value: Short, vararg indices: Int) = setValue(cast(value), *indices)
    fun setShort(value: Short, indices: IntArray1D) = setShort(value, *indices.toIntArray())

    fun setValue(value: NumericArrayND<N>) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        unsafeForEachIndices { indices ->
            setValue(copy.getValue(indices), indices)
        }
    }

    override fun setValue(value: N, vararg indices: Int)

    fun setView(value: NumericArrayND<N>, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: NumericArrayND<N>, vararg indices: IndexProgression): Unit =
            setView(value, *indices.computeIndices())

    override fun lowerRank(axis: Int): MutableNumericArrayND<N>

    override fun higherRank(axis: Int): MutableNumericArrayND<N>

    override fun arrayAlongAxis(axis: Int, index: Int): MutableNumericArrayND<N>

}
