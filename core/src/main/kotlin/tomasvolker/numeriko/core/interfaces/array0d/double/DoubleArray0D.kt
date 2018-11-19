package tomasvolker.numeriko.core.interfaces.array0d.double

import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy

interface DoubleArray0D: Array0D<Double>, DoubleArrayND {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble()
    }

    fun get(): Double = getDouble()

    override fun getValue(): Double = getDouble()

    override fun getDouble(): Double

    override fun copy(): DoubleArray0D = copy(this)

    override fun asMutable(): MutableDoubleArray0D = this as MutableDoubleArray0D

    override fun iterator(): DoubleIterator = DefaultDoubleArray0DIterator(this)

}
