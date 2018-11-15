package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.preconditions.requireValidIndices

interface MutableDoubleArray1D: DoubleArray1D, MutableArray1D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value, indices[0])
    }

    override fun setValue(value: Double, index: Int): Unit = setDouble(value, index)

    fun setDouble(value: Double, index: Int)
    fun setDouble(value: Double, index: Index) = setDouble(value, index.computeValue(size))

    fun setValue(other: DoubleArray1D) {
        requireSameSize(other, this)

        forEachIndex { i ->
            setDouble(other.getDouble(i), i)
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    fun setDouble(value: Double) {

        for (i in indices) {
            setDouble(value, i)
        }

    }

    override fun getView(indexRange: IntProgression): MutableDoubleArray1D =
            DefaultMutableDoubleArray1DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableDoubleArray1D =
            getView(indexRange.computeProgression(size))

    override operator fun get(index: IntProgression): MutableDoubleArray1D = getView(index)
    override operator fun get(index: IndexProgression): MutableDoubleArray1D = getView(index)


    fun setView(value: DoubleArray1D, indexRange: IntProgression): Unit =
            getView(indexRange).setValue(value.copy())

    fun setView(value: DoubleArray1D, indexRange: IndexProgression): Unit =
            setView(value, indexRange.computeProgression(size))

    override fun setView(value: Double, indexRange: IntProgression): Unit =
            getView(indexRange).setDouble(value)

    override fun setView(value: Double, indexRange: IndexProgression): Unit =
            setView(value, indexRange.computeProgression(size))


    operator fun set(index: Int, value: Double): Unit = setValue(value, index)

    operator fun set(index: Index, value: Double): Unit = setValue(value, index)
    operator fun set(index: IntProgression, value: Double): Unit = setView(value, index)

    operator fun set(index: IndexProgression, value: Double): Unit = setView(value, index)
    operator fun set(index: IntProgression, value: DoubleArray1D): Unit = setView(value, index)

    operator fun set(index: IndexProgression, value: DoubleArray1D): Unit = setView(value, index)


    fun applyPlus(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: DoubleArray1D): MutableDoubleArray1D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: Double): MutableDoubleArray1D =
            applyElementWise { it + other }

    fun applyMinus(other: Double): MutableDoubleArray1D =
            applyElementWise { it - other }

    fun applyTimes(other: Double): MutableDoubleArray1D =
            applyElementWise { it * other }

    fun applyDiv(other: Double): MutableDoubleArray1D =
            applyElementWise { it / other }

    fun applyPlus(other: Int): MutableDoubleArray1D =
            applyPlus(other.toDouble())

    fun applyMinus(other: Int): MutableDoubleArray1D =
            applyMinus(other.toDouble())

    fun applyTimes(other: Int): MutableDoubleArray1D =
            applyTimes(other.toDouble())

    fun applyDiv(other: Int): MutableDoubleArray1D =
            applyDiv(other.toDouble())

}
