package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.functions.FunctionDtoD
import tomasvolker.numeriko.core.functions.FunctionIAtoD
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.*
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.numeric.MutableNumericArrayND
import tomasvolker.numeriko.core.interfaces.iteration.inlinedApplyElementWise
import tomasvolker.numeriko.core.interfaces.iteration.inlinedApplyFill
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.view.ElementOrder

interface MutableDoubleArrayND: DoubleArrayND, MutableNumericArrayND<Double> {

    override fun getView(vararg indices: IntProgression): MutableDoubleArrayND =
            defaultDoubleArrayNDView(this, indices)

    override fun getView(vararg indices: IndexProgression): MutableDoubleArrayND =
            getView(*indices.computeIndices())

    fun set(value: Double) = setDouble(intArrayOf(), value)
    operator fun set(i0: Int, value: Double) = setDouble(intArrayOf(i0), value)
    operator fun set(i0: Int, i1: Int, value: Double) = setDouble(intArrayOf(i0, i1), value)
    operator fun set(i0: Int, i1: Int, i2: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3, i4), value)
    operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double) = setDouble(intArrayOf(i0, i1, i2, i3, i4, i5), value)

    operator fun set(vararg indices: Int, value: Double) = setDouble(indices, value)

    override fun setDouble(indices: IntArray, value: Double)
    override fun setFloat (indices: IntArray, value: Float) = setDouble(indices, value.toDouble())
    override fun setLong  (indices: IntArray, value: Long) = setDouble(indices, value.toDouble())
    override fun setInt   (indices: IntArray, value: Int) = setDouble(indices, value.toDouble())
    override fun setShort (indices: IntArray, value: Short) = setDouble(indices, value.toDouble())

    fun setDouble(value: Double, vararg indices: Index) = setDouble(indices.computeIndices(), value)

    override fun setValue(value: ArrayND<Double>) =
            if (value is DoubleArrayND)
                setValue(value)
            else
                super.setValue(value)

    fun setValue(value: DoubleArrayND) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.inlinedForEachIndexed { indices, element ->
            setDouble(indices, element)
        }
    }

    operator fun set(indices: IntArray1D, value: Double) = setDouble(indices, value)

    override fun setValue(indices: IntArray, value: Double) = setDouble(indices, value)

    override fun as0D(): MutableDoubleArray0D = DefaultDoubleArrayND0DView(this)
    override fun as1D(): MutableDoubleArray1D = DefaultDoubleArrayND1DView(this)
    override fun as2D(): MutableDoubleArray2D = DefaultDoubleArrayND2DView(this)

    fun setView(value: DoubleArrayND, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: DoubleArrayND, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    override fun lowerRank(axis: Int): MutableDoubleArrayND =
            defaultDoubleArrayNDLowerRankView(this, axis)

    override fun higherRank(axis: Int): MutableDoubleArrayND =
            DefaultDoubleArrayNDHigherRankView(this, axis)

    override fun arrayAlongAxis(axis: Int, index: Int): MutableDoubleArrayND =
            getView(*Array(rank) { ax ->
                if (ax == axis)
                    IntRange(index, index).toIndexProgression()
                else
                    All
            }).lowerRank(axis = axis)

    override fun linearView(order: ElementOrder): MutableDoubleArray1D =
            DefaultDoubleArrayNDLinearView(this, order)


    fun applyElementWise(function: FunctionDtoD): MutableDoubleArrayND =
            inlinedApplyElementWise { function(it) }

    fun applyFill(function: FunctionIAtoD): MutableDoubleArrayND =
            inlinedApplyFill { indices -> function(indices) }

}
