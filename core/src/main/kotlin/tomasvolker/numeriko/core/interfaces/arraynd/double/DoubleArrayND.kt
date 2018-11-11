package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.DefaultDoubleArrayNDIterator

interface DoubleArrayND: ArrayND<Double> {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    fun getDouble(vararg indices: Int): Double

    fun getDouble(indices: IntArray1D): Double =
            getValue(*indices.toIntArray())

    fun getDouble(vararg indices: Index): Double =
            getDouble(*indices.computeIndices(shape))

    override fun copy(): DoubleArrayND = TODO()

    override fun iterator(): DoubleIterator = DefaultDoubleArrayNDIterator(this)

    fun asMutable(): MutableDoubleArrayND = this as MutableDoubleArrayND

}
