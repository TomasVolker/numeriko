package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.defaultFactory

interface MutableDoubleArrayND: DoubleArrayND, MutableArrayND<Double> {

    fun setDouble(value: Double, vararg indices: Int)

    fun setDouble(value: Double, vararg indices: Index) =
            setDouble(value, *indices.computeIndices(shape))

    fun setDouble(value: Double, indices: IntArray1D) =
            setDouble(value, *indices.toIntArray())

    operator fun set(indices: IntArray1D, value: Double) =
            setDouble(value, indices)

    override fun setValue(value: Double, vararg indices: Int) =
            setDouble(value, *indices)

    override fun copy(): MutableDoubleArrayND = defaultFactory.copy(this).asMutable()

}
