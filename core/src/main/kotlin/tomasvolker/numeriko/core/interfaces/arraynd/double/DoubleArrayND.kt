package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.defaultFactory
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.operations.concatenate

interface DoubleArrayND: ArrayND<Double> {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    fun getDouble(vararg indices: Int): Double

    fun getDouble(): Double = getDouble(*intArrayOf())

    fun getDouble(indices: IntArray1D): Double =
            getValue(*indices.toIntArray())

    fun getDouble(vararg indices: Index): Double =
            getDouble(*indices.computeIndices(shape))

    operator fun get(indices: IntArray1D): Double =
            getDouble(indices)

    override fun getView(vararg indices: IntProgression): DoubleArrayND =
            DefaultMutableDoubleArrayNDView(
                    array = this.asMutable(),
                    offset = intArray1D(indices.map { it.first }.toIntArray()),
                    shape = intArray1D(indices.map { it.count() }.toIntArray()),
                    stride = intArray1D(indices.map { it.step }.toIntArray())
            )

    override fun getView(vararg indices: IndexProgression): DoubleArrayND =
            getView(*indices.computeIndices(shape))

    override fun copy(): DoubleArrayND = defaultFactory.copy(this)

    override fun iterator(): DoubleIterator = DefaultDoubleArrayNDIterator(this)

    override fun asMutable(): MutableDoubleArrayND = this as MutableDoubleArrayND


    infix fun outer(other: DoubleArrayND): DoubleArrayND =
            doubleArrayND(this.shape concatenate other.shape) { indices ->
                val thisIndices = indices[0 until this.rank]
                val otherIndices = indices[this.rank until Last]
                this[thisIndices] * other[otherIndices]
            }
/*
    fun contract(index0: Int, index1: Int): DoubleArrayND {
        require(index0 != index1 && shape[index0] == shape[index1])
        val reductionSize = shape[index0]
        return doubleArrayND(shape) { indices ->
            sumDouble(0 until reductionSize) { r ->
                this[..,r,..,r,..]
            }
        }
    }
    */
}
