package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

interface MutableDoubleArray0D: DoubleArray0D, MutableArray0D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value)
    }

    override fun setValue(value: Double): Unit = setDouble(value)

    override fun setDouble(value: Double)

    fun setValue(other: DoubleArray0D) {
        setDouble(other.getDouble())
    }

    fun set(value: Double): Unit = setDouble(value)

}
