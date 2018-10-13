package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableDoubleArray2D: DoubleArray2D, MutableArray2D<Double> {

    fun setDouble(value: Double, i0: Int, i1: Int)

    fun setDouble(value: Double, i0: Index, i1: Index) =
            setDouble(value, i0.computeValue(shape0), i1.computeValue(shape1))

    override fun setValue(value: Double, i0: Int, i1: Int) =
            setDouble(value, i0, i1)

    fun setValue(other: DoubleArray2D) {

        requireSameShape(this, other)

        for (i0 in indices0) {
            for (i1 in indices1) {
                setDouble(other.getDouble(i0, i1), i0, i1)
            }
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    fun setDouble(value: Double) {

        for (i0 in indices0) {
            for (i1 in indices1) {
                setDouble(value, i0, i1)
            }
        }

    }
/*
    override fun getView(indexRange: IntProgression): MutableDoubleArray2D =
            DefaultMutableDoubleArray2DView(
                    array = this,
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): MutableDoubleArray2D =
            getView(indexRange.computeProgression(size))

    fun setView(value: DoubleArray1D, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    // TODO Avoid copy when possible
    fun setView(value: DoubleArray1D, indexRange: IntProgression) =
            getView(indexRange).setValue(value.copy())

    override fun setView(value: Double, indexRange: IndexProgression) =
            setView(value, indexRange.computeProgression(size))

    override fun setView(value: Double, indexRange: IntProgression) =
            getView(indexRange).setDouble(value)
*/
    override fun copy(): MutableDoubleArray2D = /*mutableCopy(this)*/ TODO()
/*
    override operator fun get(index: IntProgression): MutableDoubleArray2D = getView(index)
    override operator fun get(index: IndexProgression): MutableDoubleArray2D = getView(index)
*/
    operator fun set(i0: Int, i1: Int, value: Double) = setValue(value, i0, i1)
    operator fun set(i0: Index, i1: Index, value: Double) = setValue(value, i0.computeValue(shape0), i1.computeValue(shape1))
    /*
    operator fun set(index: IntProgression, value: Double) = setView(value, index)
    operator fun set(index: IndexProgression, value: Double) = setView(value, index)

    operator fun set(index: IntProgression, value: DoubleArray1D) = setView(value, index)
    operator fun set(index: IndexProgression, value: DoubleArray1D) = setView(value, index)
*/
    fun applyPlus(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t + o }

    fun applyMinus(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t - o }

    fun applyTimes(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t * o }

    fun applyDiv(other: DoubleArray2D): MutableDoubleArray2D =
            applyElementWise(other) { t, o -> t / o }

    fun applyPlus(other: Double): MutableDoubleArray2D =
            applyMap { it + other }

    fun applyMinus(other: Double): MutableDoubleArray2D =
            applyMap { it - other }

    fun applyTimes(other: Double): MutableDoubleArray2D =
            applyMap { it * other }

    fun applyDiv(other: Double): MutableDoubleArray2D =
            applyMap { it / other }

    fun applyPlus(other: Int): MutableDoubleArray2D =
            applyPlus(other.toDouble())

    fun applyMinus(other: Int): MutableDoubleArray2D =
            applyMinus(other.toDouble())

    fun applyTimes(other: Int): MutableDoubleArray2D =
            applyTimes(other.toDouble())

    fun applyDiv(other: Int): MutableDoubleArray2D =
            applyDiv(other.toDouble())

}
