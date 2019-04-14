package tomasvolker.numeriko.lowrank.interfaces.array2d.numeric

import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.MutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableNumericArray2D<N: Number>: NumericArray2D<N>, MutableArray2D<N>, MutableNumericArrayND<N> {

    override fun setDouble(indices: IntArray, value: Double) = setValue(indices, cast(value))
    override fun setDouble(indices: IntArray1D, value: Double) = setDouble(indices.toIntArray(), value)

    override fun setFloat(indices: IntArray, value: Float) = setValue(indices, cast(value))
    override fun setFloat(indices: IntArray1D, value: Float) = setFloat(indices.toIntArray(), value)

    override fun setLong(indices: IntArray, value: Long) = setValue(indices, cast(value))
    override fun setLong(indices: IntArray1D, value: Long) = setLong(indices.toIntArray(), value)

    override fun setInt(indices: IntArray, value: Int) = setValue(indices, cast(value))
    override fun setInt(indices: IntArray1D, value: Int) = setInt(indices.toIntArray(), value)

    override fun setShort(indices: IntArray, value: Short) = setValue(indices, cast(value))
    override fun setShort(indices: IntArray1D, value: Short) = setShort(indices.toIntArray(), value)

    fun setDouble(i0: Int, i1: Int, value: Double) = setValue(i0, i1, cast(value))
    fun setFloat (i0: Int, i1: Int, value: Float) = setValue(i0, i1, cast(value))
    fun setLong  (i0: Int, i1: Int, value: Long) = setValue(i0, i1, cast(value))
    fun setInt   (i0: Int, i1: Int, value: Int) = setValue(i0, i1, cast(value))
    fun setShort (i0: Int, i1: Int, value: Short) = setValue(i0, i1, cast(value))

    fun setValue(value: NumericArray2D<N>) {
        requireSameShape(this, value)
        // Anti alias copy
        value.copy().inlinedForEachIndexed { indices, element ->
            setValue(indices, element)
        }
    }

    override fun copy(): MutableNumericArray2D<N> = TODO()

    override fun setValue(indices: IntArray, value: N)

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
