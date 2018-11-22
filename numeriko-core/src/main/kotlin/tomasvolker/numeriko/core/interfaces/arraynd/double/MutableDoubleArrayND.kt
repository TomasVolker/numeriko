package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeForEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.defaultArrayNDView
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableDoubleArrayND: DoubleArrayND, MutableNumericArrayND<Double> {

    override fun getView(vararg indices: IntProgression): MutableDoubleArrayND =
            defaultDoubleArrayNDView(this, indices)

    override fun getView(vararg indices: IndexProgression): MutableDoubleArrayND =
            getView(*indices.computeIndices())

    fun setDouble(value: Double) = setDouble(value, *intArrayOf())

    override fun setDouble(value: Double, vararg indices: Int)
    override fun setFloat (value: Float , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setLong  (value: Long  , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setInt   (value: Int   , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setShort (value: Short , vararg indices: Int) = setDouble(value.toDouble(), *indices)

    fun setDouble(value: Double, vararg indices: Index) = setDouble(value, *indices.computeIndices())

    fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        unsafeForEachIndices { indices ->
            setDouble(copy.getDouble(indices), indices)
        }
    }

    operator fun set(indices: IntArray1D, value: Double) = setDouble(value, indices)

    override fun setValue(value: Double, vararg indices: Int) = setDouble(value, *indices)

    override fun as0D(): MutableDoubleArray0D = DefaultDoubleArrayND0DView(this)
    override fun as1D(): MutableDoubleArray1D = DefaultDoubleArrayND1DView(this)
    override fun as2D(): MutableDoubleArray2D = DefaultDoubleArrayND2DView(this)

    fun setView(value: DoubleArrayND, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: DoubleArrayND, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    override fun lowerRank(axis: Int): MutableDoubleArrayND =
            DefaultDoubleArrayNDLowerRankView(this, axis)

    override fun higherRank(axis: Int): MutableDoubleArrayND =
            DefaultDoubleArrayNDHigherRankView(this, axis)

    override fun arrayAlongAxis(axis: Int, index: Int): MutableDoubleArrayND =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    fun applyPlus (other: Double): MutableDoubleArrayND = applyElementWise { it + other }
    fun applyMinus(other: Double): MutableDoubleArrayND = applyElementWise { it - other }
    fun applyTimes(other: Double): MutableDoubleArrayND = applyElementWise { it * other }
    fun applyDiv  (other: Double): MutableDoubleArrayND = applyElementWise { it / other }

    fun applyPlus (other: Int): MutableDoubleArrayND = applyPlus (other.toDouble())
    fun applyMinus(other: Int): MutableDoubleArrayND = applyMinus(other.toDouble())
    fun applyTimes(other: Int): MutableDoubleArrayND = applyTimes(other.toDouble())
    fun applyDiv  (other: Int): MutableDoubleArrayND = applyDiv  (other.toDouble())

}
