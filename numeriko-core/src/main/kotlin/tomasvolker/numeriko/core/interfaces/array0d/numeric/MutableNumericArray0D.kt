package tomasvolker.numeriko.core.interfaces.array0d.numeric

import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND

interface MutableNumericArray0D<N: Number>: NumericArray0D<N>, MutableArray0D<N>, MutableNumericArrayND<N> {

    override fun setValue(indices: IntArray, value: N) {
        requireValidIndices(indices)
        setValue(indices, value)
    }

    fun setDouble(value: Double) = setValue(cast(value))
    fun setFloat (value: Float ) = setValue(cast(value))
    fun setLong  (value: Long  ) = setValue(cast(value))
    fun setInt   (value: Int   ) = setValue(cast(value))
    fun setShort (value: Short ) = setValue(cast(value))

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        setDouble(value)
    }

    override fun setFloat(indices: IntArray, value: Float) {
        requireValidIndices(indices)
        setFloat(value)
    }

    override fun setLong(indices: IntArray, value: Long) {
        requireValidIndices(indices)
        setLong(value)
    }

    override fun setInt(indices: IntArray, value: Int) {
        requireValidIndices(indices)
        setInt(value)
    }

    override fun setShort(indices: IntArray, value: Short) {
        requireValidIndices(indices)
        setShort(value)
    }

    override fun getView(): MutableNumericArray0D<N> = this

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<NumericArray0D>.arrayAlongAxis(axis, index)
    }

    override fun lowerRank(axis: Int): Nothing {
        super<NumericArray0D>.lowerRank(axis)
    }

    override fun higherRank(): MutableNumericArray1D<N>
    override fun higherRank(axis: Int): MutableNumericArray1D<N>

    fun setValue(other: NumericArray0D<N>) {
        setValue(other.getValue())
    }
}
