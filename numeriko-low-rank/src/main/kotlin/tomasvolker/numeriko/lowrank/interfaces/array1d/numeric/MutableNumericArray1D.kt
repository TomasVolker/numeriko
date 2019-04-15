package tomasvolker.numeriko.lowrank.interfaces.array1d.numeric

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array2d.numeric.MutableNumericArray2D
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND

interface MutableNumericArray1D<N: Number>: NumericArray1D<N>, MutableArray1D<N>, MutableNumericArrayND<N> {

    override fun setValue(indices: IntArray, value: N) {
        requireValidIndices(indices)
        setValue(indices, value)
    }

    fun setDouble(i0: Int, value: Double) = setValue(i0, cast(value))
    fun setFloat (i0: Int, value: Float) = setValue(i0, cast(value))
    fun setLong  (i0: Int, value: Long) = setValue(i0, cast(value))
    fun setInt   (i0: Int, value: Int) = setValue(i0, cast(value))
    fun setShort (i0: Int, value: Short) = setValue(i0, cast(value))

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        setDouble(indices, value)
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        setFloat(indices, value)
    }

    override fun setLong(indices: IntArray, value: Long) {
        requireValidIndices(indices)
        setLong(indices, value)
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        setInt(indices, value)
    }

    override fun setShort(indices: IntArray, value: Short) {
        requireValidIndices(indices)
        setShort(indices, value)
    }

    override fun getView(vararg indices: IntProgression): MutableNumericArray1D<N> {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun copy(): MutableNumericArray1D<N>

    override fun getView(i0: Int): MutableNumericArray0D<N>
    override fun getView(i0: IntProgression): MutableNumericArray1D<N>

    override fun getView(i0: Index): MutableNumericArray0D<N> = getView(i0.compute())
    override fun getView(i0: IndexProgression): MutableNumericArray1D<N> = getView(i0.compute())

    override fun lowerRank(axis: Int): MutableNumericArray0D<N>

    override fun higherRank(axis: Int): MutableNumericArray2D<N>

    override fun arrayAlongAxis(axis: Int, index: Int): MutableNumericArray0D<N>

    fun setValue(other: NumericArray1D<N>) {
        forEachIndex { i0 ->
            setValue(i0, other.getValue(i0))
        }
    }
}
