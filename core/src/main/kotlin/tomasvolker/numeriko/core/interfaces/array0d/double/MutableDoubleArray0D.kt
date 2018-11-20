package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array0d.numeric.MutableNumericArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

interface MutableDoubleArray0D: DoubleArray0D, MutableNumericArray0D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value)
    }

    override fun setValue(value: Double): Unit = setDouble(value)

    override fun setFloat(value: Float, vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setLong (value: Long , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setInt  (value: Int  , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setShort(value: Short, vararg indices: Int) = setDouble(value.toDouble(), *indices)

    override fun setDouble(value: Double)

    override fun setFloat (value: Float ) = setDouble(value.toDouble())
    override fun setLong  (value: Long  ) = setDouble(value.toDouble())
    override fun setInt   (value: Int   ) = setDouble(value.toDouble())
    override fun setShort (value: Short ) = setDouble(value.toDouble())

    override fun getView(): MutableDoubleArray0D = this

    fun setValue(other: DoubleArray0D) {
        setDouble(other.getDouble())
    }

    fun set(value: Double): Unit = setDouble(value)

}
