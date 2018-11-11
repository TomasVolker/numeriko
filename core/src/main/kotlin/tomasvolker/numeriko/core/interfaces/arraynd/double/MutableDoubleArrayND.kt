package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND

interface MutableDoubleArrayND: DoubleArrayND, MutableArrayND<Double> {

    fun setDouble(value: Double, vararg indices: Int)

    fun setDouble(value: Double, vararg indices: Index) =
            setDouble(value, *indices.computeIndices(shape))

    override fun setValue(value: Double, vararg indices: Int) =
            setDouble(value, *indices)

    override fun copy(): MutableDoubleArrayND = TODO()

}
