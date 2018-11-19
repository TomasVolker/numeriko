package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireSameSize

interface MutableDoubleArray1D: DoubleArray1D, MutableArray1D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value, indices[0])
    }

    override fun setValue(value: Double, i0: Int): Unit = setDouble(value, i0)

    fun setDouble(value: Double, i0: Int)
    fun setDouble(value: Double, i0: Index) = setDouble(value, i0.computeValue(size))

    override fun lowerRank(axis: Int): MutableDoubleArray0D {
        requireValidAxis(axis)
        return defaultDoubleArray0DView(this, 0)
    }

    fun setValue(other: DoubleArray1D) {
        requireSameSize(other, this)
        // Anti alias copy
        val copy = other.copy()
        forEachIndex { i ->
            setDouble(copy.getDouble(i), i)
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    override fun setDouble(value: Double) {

        for (i in indices) {
            setDouble(value, i)
        }

    }

    override fun getView(i0: IntProgression): MutableDoubleArray1D = defaultDoubleArray1DView(this, i0)
    override fun getView(i0: IndexProgression): MutableDoubleArray1D = getView(i0.compute())

    override operator fun get(i0: IntProgression): MutableDoubleArray1D = getView(i0)
    override operator fun get(i0: IndexProgression): MutableDoubleArray1D = getView(i0)


    fun setView(value: DoubleArray1D, i0: IntProgression): Unit = getView(i0).setValue(value)
    fun setView(value: DoubleArray1D, i0: IndexProgression): Unit = setView(value, i0.compute())

    override fun setView(value: Double, i0: IntProgression): Unit = getView(i0).setDouble(value)
    override fun setView(value: Double, i0: IndexProgression): Unit = setView(value, i0.compute())


    operator fun set(i0: Int  , value: Double): Unit = setValue(value, i0)
    operator fun set(i0: Index, value: Double): Unit = setValue(value, i0)

    operator fun set(i0: IntProgression  , value: Double): Unit = setView(value, i0)
    operator fun set(i0: IndexProgression, value: Double): Unit = setView(value, i0)

    operator fun set(i0: IntProgression  , value: DoubleArray1D): Unit = setView(value, i0)
    operator fun set(i0: IndexProgression, value: DoubleArray1D): Unit = setView(value, i0)


    fun applyPlus (other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t + o }
    fun applyMinus(other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t - o }
    fun applyTimes(other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t * o }
    fun applyDiv  (other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t / o }

    override fun applyPlus (other: Double): MutableDoubleArray1D = applyElementWise { it + other }
    override fun applyMinus(other: Double): MutableDoubleArray1D = applyElementWise { it - other }
    override fun applyTimes(other: Double): MutableDoubleArray1D = applyElementWise { it * other }
    override fun applyDiv  (other: Double): MutableDoubleArray1D = applyElementWise { it / other }

    override fun applyPlus (other: Int): MutableDoubleArray1D = applyPlus(other.toDouble())
    override fun applyMinus(other: Int): MutableDoubleArray1D = applyMinus(other.toDouble())
    override fun applyTimes(other: Int): MutableDoubleArray1D = applyTimes(other.toDouble())
    override fun applyDiv  (other: Int): MutableDoubleArray1D = applyDiv(other.toDouble())

}
