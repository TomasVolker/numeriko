package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.until
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.computeIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.indices
import tomasvolker.numeriko.core.interfaces.factory.defaultFactory
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.operations.concatenate
import tomasvolker.numeriko.core.operations.inject
import tomasvolker.numeriko.core.operations.remove
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.max
import kotlin.math.min

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

    fun contract(axis0: Int, axis1: Int): DoubleArrayND {
        val index0 = min(axis0, axis1)
        val index1 = max(axis0, axis1)
        require(index0 != index1 && shape[index0] == shape[index1])
        val newShape = shape.remove(index0).remove(index1-1)
        return doubleArrayND(newShape) { indices ->
            sumDouble(indices(index0)) { r ->
                this[indices.inject(index = index0, value = r).inject(index = index1, value = r)]
            }
        }
    }

}
