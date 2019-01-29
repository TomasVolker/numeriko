package tomasvolker.numeriko.core.interfaces.arraynd.numeric

import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.performance.fastForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableNumericArrayND<N: Number>: NumericArrayND<N>, MutableArrayND<N> {

    override fun getView(vararg indices: IntProgression): MutableNumericArrayND<N>

    override fun getView(vararg indices: IndexProgression): MutableNumericArrayND<N> =
            getView(*indices.computeIndices())

    fun setDouble(indices: IntArray, value: Double) = setValue(indices, cast(value))
    fun setDouble(indices: IntArray1D, value: Double) = setDouble(indices.toIntArray(), value)

    fun setFloat(indices: IntArray, value: Float) = setValue(cast(value))
    fun setFloat(indices: IntArray1D, value: Float) = setFloat(indices.toIntArray(), value)

    fun setLong(indices: IntArray, value: Long) = setValue(indices, cast(value))
    fun setLong(indices: IntArray1D, value: Long) = setLong(indices.toIntArray(), value)

    fun setInt(indices: IntArray, value: Int) = setValue(cast(value))
    fun setInt(indices: IntArray1D, value: Int) = setInt(indices.toIntArray(), value)

    fun setShort(indices: IntArray, value: Short) = setValue(indices, cast(value))
    fun setShort(indices: IntArray1D, value: Short) = setShort(indices.toIntArray(), value)

    fun setValue(value: NumericArrayND<N>) {
        requireSameShape(this, value)
        // Anti alias copy
        value.copy().fastForEachIndexed { indices, element ->
            setValue(indices, element)
        }
    }

    override fun setValue(indices: IntArray, value: N)

    fun setView(value: NumericArrayND<N>, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: NumericArrayND<N>, vararg indices: IndexProgression): Unit =
            setView(value, *indices.computeIndices())

    override fun lowerRank(axis: Int): MutableNumericArrayND<N>

    override fun higherRank(axis: Int): MutableNumericArrayND<N>

    override fun arrayAlongAxis(axis: Int, index: Int): MutableNumericArrayND<N>

}
