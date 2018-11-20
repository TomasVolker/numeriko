package tomasvolker.numeriko.core.interfaces.array0d.numeric

import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND

interface MutableNumericArray0D<N: Number>: NumericArray0D<N>, MutableArray0D<N>, MutableNumericArrayND<N> {

    override fun setValue(value: N, vararg indices: Int) {
        requireValidIndices(indices)
        setValue(value, *indices)
    }

    fun setDouble(value: Double) = setValue(cast(value))
    fun setFloat (value: Float ) = setValue(cast(value))
    fun setLong  (value: Long  ) = setValue(cast(value))
    fun setInt   (value: Int   ) = setValue(cast(value))
    fun setShort (value: Short ) = setValue(cast(value))

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

    override fun getView(): MutableNumericArray0D<N> = this

    fun setValue(other: NumericArray0D<N>) {
        setValue(other.getValue())
    }
}
