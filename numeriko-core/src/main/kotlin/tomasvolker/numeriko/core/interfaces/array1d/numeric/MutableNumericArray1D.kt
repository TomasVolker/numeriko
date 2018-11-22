package tomasvolker.numeriko.core.interfaces.array1d.numeric

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array2d.numeric.MutableNumericArray2D
import tomasvolker.numeriko.core.interfaces.array2d.numeric.NumericArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND

interface MutableNumericArray1D<N: Number>: NumericArray1D<N>, MutableArray1D<N>, MutableNumericArrayND<N> {

    override fun setValue(value: N, vararg indices: Int) {
        requireValidIndices(indices)
        setValue(value, *indices)
    }

    fun setDouble(value: Double, i0: Int) = setValue(cast(value), i0)
    fun setFloat (value: Float , i0: Int) = setValue(cast(value), i0)
    fun setLong  (value: Long  , i0: Int) = setValue(cast(value), i0)
    fun setInt   (value: Int   , i0: Int) = setValue(cast(value), i0)
    fun setShort (value: Short , i0: Int) = setValue(cast(value), i0)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value)
    }

    override fun setFloat(value: Float, vararg indices: Int) {
        requireValidIndices(indices)
        setFloat(value)
    }

    override fun setLong(value: Long, vararg indices: Int) {
        requireValidIndices(indices)
        setLong(value)
    }

    override fun setInt(value: Int, vararg indices: Int) {
        requireValidIndices(indices)
        setInt(value)
    }

    override fun setShort(value: Short, vararg indices: Int) {
        requireValidIndices(indices)
        setShort(value)
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
            setValue(other.getValue(i0), i0)
        }
    }
}
