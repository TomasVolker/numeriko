package tomasvolker.numeriko.core.interfaces.array2d.numeric

import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeForEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableNumericArray2D<N: Number>: NumericArray2D<N>, MutableArray2D<N>, MutableNumericArrayND<N> {

    override fun setDouble(value: Double, vararg indices: Int) = setValue(cast(value), *indices)
    override fun setDouble(value: Double, indices: IntArray1D) = setDouble(value, *indices.toIntArray())

    override fun setFloat(value: Float, vararg indices: Int) = setValue(cast(value))
    override fun setFloat(value: Float, indices: IntArray1D) = setFloat(value, *indices.toIntArray())

    override fun setLong(value: Long, vararg indices: Int) = setValue(cast(value), *indices)
    override fun setLong(value: Long, indices: IntArray1D) = setLong(value, *indices.toIntArray())

    override fun setInt(value: Int, vararg indices: Int) = setValue(cast(value))
    override fun setInt(value: Int, indices: IntArray1D) = setInt(value, *indices.toIntArray())

    override fun setShort(value: Short, vararg indices: Int) = setValue(cast(value), *indices)
    override fun setShort(value: Short, indices: IntArray1D) = setShort(value, *indices.toIntArray())

    fun setDouble(value: Double, i0: Int, i1: Int) = setValue(cast(value), i0, i1)
    fun setFloat (value: Float , i0: Int, i1: Int) = setValue(cast(value), i0, i1)
    fun setLong  (value: Long  , i0: Int, i1: Int) = setValue(cast(value), i0, i1)
    fun setInt   (value: Int   , i0: Int, i1: Int) = setValue(cast(value), i0, i1)
    fun setShort (value: Short , i0: Int, i1: Int) = setValue(cast(value), i0, i1)

    fun setValue(value: NumericArray2D<N>) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        unsafeForEachIndices { indices ->
            setValue(copy.getValue(indices), indices)
        }
    }

    override fun copy(): MutableNumericArray2D<N> = TODO()

    override fun setValue(value: N, vararg indices: Int)

    fun setView(value: NumericArray2D<N>, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: NumericArray2D<N>, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    override fun getView(i0: Int, i1: Int): MutableNumericArray0D<N>

    override fun getView(i0: Int, i1: IntProgression): MutableNumericArray1D<N>
    override fun getView(i0: IntProgression, i1: Int): MutableNumericArray1D<N>

    override fun getView(i0: IntProgression, i1: IntProgression): MutableNumericArray2D<N>

    override fun getView(vararg indices: IntProgression): MutableNumericArray2D<N>

    override fun getView(vararg indices: IndexProgression): MutableNumericArray2D<N> =
            getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): MutableNumericArray1D<N>

    override fun arrayAlongAxis(axis: Int, index: Int): MutableNumericArray1D<N>

}
