package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeForEachIndices

interface MutableDoubleArrayND: DoubleArrayND, MutableArrayND<Double> {

    fun setDouble(value: Double, vararg indices: Int)

    fun setDouble(value: Double, vararg indices: Index) = setDouble(value, *indices.computeIndices())

    fun setDouble(value: Double, indices: IntArray1D) = setDouble(value, *indices.toIntArray())

    fun setValue(value: DoubleArrayND) {
        unsafeForEachIndices { indices ->
            setDouble(value.getDouble(indices), indices)
        }
    }

    operator fun set(indices: IntArray1D, value: Double) = setDouble(value, indices)

    override fun setValue(value: Double, vararg indices: Int) = setDouble(value, *indices)

    fun setView(value: DoubleArrayND, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: DoubleArrayND, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    fun applyPlus (other: Double): MutableDoubleArrayND = applyElementWise { it + other }
    fun applyMinus(other: Double): MutableDoubleArrayND = applyElementWise { it - other }
    fun applyTimes(other: Double): MutableDoubleArrayND = applyElementWise { it * other }
    fun applyDiv  (other: Double): MutableDoubleArrayND = applyElementWise { it / other }

    fun applyPlus (other: Int): MutableDoubleArrayND = applyPlus(other.toDouble())
    fun applyMinus(other: Int): MutableDoubleArrayND = applyMinus(other.toDouble())
    fun applyTimes(other: Int): MutableDoubleArrayND = applyTimes(other.toDouble())
    fun applyDiv  (other: Int): MutableDoubleArrayND = applyDiv(other.toDouble())

}
