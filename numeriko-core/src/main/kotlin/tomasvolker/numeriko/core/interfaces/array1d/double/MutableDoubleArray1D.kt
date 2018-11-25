package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleArray1DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleReshapedView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array1d.generic.view.DefaultReshapedView
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.view.ElementOrder

interface MutableDoubleArray1D: DoubleArray1D, MutableNumericArray1D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int): Unit =
            setDouble(value, *indices)

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value, indices[0])
    }

    override fun setFloat(value: Float, vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setLong (value: Long , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setInt  (value: Int  , vararg indices: Int) = setDouble(value.toDouble(), *indices)
    override fun setShort(value: Short, vararg indices: Int) = setDouble(value.toDouble(), *indices)

    override fun setValue(value: Double, i0: Int): Unit = setDouble(value, i0)

    override fun setDouble(value: Double, i0: Int)
    override fun setFloat (value: Float , i0: Int) = setDouble(value.toDouble(), i0)
    override fun setLong  (value: Long  , i0: Int) = setDouble(value.toDouble(), i0)
    override fun setInt   (value: Int   , i0: Int) = setDouble(value.toDouble(), i0)
    override fun setShort (value: Short , i0: Int) = setDouble(value.toDouble(), i0)

    fun setDouble(value: Double, i0: Index) = setDouble(value, i0.computeValue(size))

    override fun withShape(shape0: Int, shape1: Int, order: ElementOrder): MutableDoubleArray2D =
            withShape(intArray1DOf(shape0, shape1), order).as2D()

    override fun withShape(shape: IntArray1D, order: ElementOrder): MutableDoubleArrayND =
            DefaultDoubleReshapedView(
                    shape = shape.copy(),
                    array = this,
                    offset = 0,
                    order = order
            )

    override fun lowerRank(axis: Int): MutableDoubleArray0D {
        requireValidAxis(axis)
        return defaultDoubleArray0DView(this, 0)
    }

    override fun higherRank(axis: Int): MutableDoubleArray2D {
        require(axis in 0..1)
        return DefaultDoubleArray1DHigherRankView(this, axis)
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

    override fun getView(vararg indices: IntProgression): MutableDoubleArray1D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(i0: Int  ): MutableDoubleArray0D = defaultDoubleArray0DView(this, i0)
    override fun getView(i0: Index): MutableDoubleArray0D = getView(i0.compute())

    override fun getView(i0: IntProgression): MutableDoubleArray1D = defaultDoubleArray1DView(this, i0)
    override fun getView(i0: IndexProgression): MutableDoubleArray1D = getView(i0.compute())

    override fun arrayAlongAxis(axis: Int, index: Int): MutableDoubleArray0D {
        requireValidAxis(axis)
        return getView(index)
    }

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

    override fun copy(): MutableDoubleArray1D = copy(this).asMutable()

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
