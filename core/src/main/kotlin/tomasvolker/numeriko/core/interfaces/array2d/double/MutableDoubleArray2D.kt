package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.defaultDoubleArray2DView
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableDoubleArray2D: DoubleArray2D, MutableArray2D<Double>, MutableDoubleArrayND {

    override fun setValue(value: Double, vararg indices: Int) {
        setDouble(value, *indices)
    }

    override fun setDouble(value: Double, vararg indices: Int) {
        requireValidIndices(indices)
        setDouble(value, indices[0], indices[1])
    }

    fun setDouble(value: Double, i0: Int  , i1: Int)
    fun setDouble(value: Double, i0: Int  , i1: Index) = setDouble(value, i0.compute(0), i1.compute(1))
    fun setDouble(value: Double, i0: Index, i1: Int  ) = setDouble(value, i0.compute(0), i1.compute(1))
    fun setDouble(value: Double, i0: Index, i1: Index) = setDouble(value, i0.compute(0), i1.compute(1))

    override fun setValue(value: Double, i0: Int, i1: Int) = setDouble(value, i0, i1)

    fun setValue(other: DoubleArray2D) {

        requireSameShape(this, other)

        forEachIndex { i0, i1 ->
            setDouble(other.getDouble(i0, i1), i0, i1)
        }

    }

    override fun setValue(value: Double) = setDouble(value)

    fun setDouble(value: Double) {

        forEachIndex { i0, i1 ->
            setDouble(value, i0, i1)
        }

    }

    override fun getView(i0: Int  , i1: IntProgression  ): MutableDoubleArray1D = defaultDoubleArray2DView(this, i0, i1)
    override fun getView(i0: Int  , i1: IndexProgression): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: Int): MutableDoubleArray1D = defaultDoubleArray2DView(this, i0, i1)

    override fun getView(i0: IndexProgression, i1: Int  ): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IntProgression  , i1: Index): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): MutableDoubleArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: IntProgression): MutableDoubleArray2D = defaultDoubleArray2DView(this, i0, i1)
    override fun getView(i0: IntProgression  , i1: IndexProgression): MutableDoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): MutableDoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): MutableDoubleArray2D = getView(i0.compute(0), i1.compute(1))

    fun setView(value: DoubleArray1D, i0: Int  , i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: DoubleArray1D, i0: Index, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: Int  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: Index, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: DoubleArray1D, i0: IntProgression  , i1: Int  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: DoubleArray1D, i0: IntProgression  , i1: Index) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: IndexProgression, i1: Int  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: IndexProgression, i1: Index) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: DoubleArray2D, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setValue(value.copy())
    fun setView(value: DoubleArray2D, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray2D, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray2D, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

    override fun setView(value: Double, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setDouble(value)
    override fun setView(value: Double, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    override fun setView(value: Double, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))
    override fun setView(value: Double, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))

    override fun copy(): MutableDoubleArray2D = copy(this).asMutable()

    // Get operators

    override operator fun get(i0: Int  , i1: IntProgression  ): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: Index, i1: IntProgression  ): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: Int  , i1: IndexProgression): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: Index, i1: IndexProgression): MutableDoubleArray1D = getView(i0, i1)

    override operator fun get(i0: IntProgression  , i1: Int  ): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: IntProgression  , i1: Index): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: Int  ): MutableDoubleArray1D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: Index): MutableDoubleArray1D = getView(i0, i1)

    override operator fun get(i0: IntProgression  , i1: IntProgression  ): MutableDoubleArray2D = getView(i0, i1)
    override operator fun get(i0: IntProgression  , i1: IndexProgression): MutableDoubleArray2D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: IntProgression  ): MutableDoubleArray2D = getView(i0, i1)
    override operator fun get(i0: IndexProgression, i1: IndexProgression): MutableDoubleArray2D = getView(i0, i1)

    // Set operators

    operator fun set(i0: Int  , i1: Int  , value: Double) = setDouble(value, i0, i1)
    operator fun set(i0: Int  , i1: Index, value: Double) = setDouble(value, i0, i1)
    operator fun set(i0: Index, i1: Int  , value: Double) = setDouble(value, i0, i1)
    operator fun set(i0: Index, i1: Index, value: Double) = setDouble(value, i0, i1)

    operator fun set(i0: IntProgression  , i1: Int  , value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: IntProgression  , i1: Index, value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: Int  , value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: Index, value: DoubleArray1D) = setView(value, i0, i1)

    operator fun set(i0: Int  , i1: IntProgression  , value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: Int  , i1: IndexProgression, value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: Index, i1: IntProgression  , value: DoubleArray1D) = setView(value, i0, i1)
    operator fun set(i0: Index, i1: IndexProgression, value: DoubleArray1D) = setView(value, i0, i1)

    operator fun set(i0: IntProgression  , i1: IntProgression  , value: DoubleArray2D) = setView(value, i0, i1)
    operator fun set(i0: IntProgression  , i1: IndexProgression, value: DoubleArray2D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: IntProgression  , value: DoubleArray2D) = setView(value, i0, i1)
    operator fun set(i0: IndexProgression, i1: IndexProgression, value: DoubleArray2D) = setView(value, i0, i1)


    fun applyPlus (other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t + o }
    fun applyMinus(other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t - o }
    fun applyTimes(other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t * o }
    fun applyDiv  (other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t / o }

    override fun applyPlus (other: Double): MutableDoubleArray2D = applyElementWise { it + other }
    override fun applyMinus(other: Double): MutableDoubleArray2D = applyElementWise { it - other }
    override fun applyTimes(other: Double): MutableDoubleArray2D = applyElementWise { it * other }
    override fun applyDiv  (other: Double): MutableDoubleArray2D = applyElementWise { it / other }

    override fun applyPlus (other: Int): MutableDoubleArray2D = applyPlus(other.toDouble())
    override fun applyMinus(other: Int): MutableDoubleArray2D = applyMinus(other.toDouble())
    override fun applyTimes(other: Int): MutableDoubleArray2D = applyTimes(other.toDouble())
    override fun applyDiv  (other: Int): MutableDoubleArray2D = applyDiv(other.toDouble())

}
