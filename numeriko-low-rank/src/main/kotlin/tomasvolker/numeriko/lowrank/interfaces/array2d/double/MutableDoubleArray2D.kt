package tomasvolker.numeriko.lowrank.interfaces.array2d.double

import tomasvolker.numeriko.core.annotations.*
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.view.MutableDoubleArray2DLowerRankView
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.view.defaultDoubleArray2DView
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.*
import tomasvolker.numeriko.lowrank.interfaces.array2d.numeric.MutableNumericArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError2DMessage
import tomasvolker.numeriko.core.preconditions.requireSameShape

interface MutableDoubleArray2D: DoubleArray2D, MutableNumericArray2D<Double>, MutableDoubleArrayND {

    override fun setValue(indices: IntArray, value: Double) {
        setDouble(indices, value)
    }

    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        setDouble(indices[0], indices[1], value)
    }

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun set(value: Double): Nothing = rankError(0)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, value: Double): Nothing = rankError(1)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double): Nothing = rankError(3)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double): Nothing = rankError(4)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double): Nothing = rankError(5)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double): Nothing = rankError(6)

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun set(vararg indices: Int, value: Double): Nothing = rankError(-1)

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as0D(): Nothing = rankError(0)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)

    override fun as2D() = this

    override fun setFloat(indices: IntArray, value: Float) = setDouble(indices, value.toDouble())
    override fun setLong (indices: IntArray, value: Long) = setDouble(indices, value.toDouble())
    override fun setInt  (indices: IntArray, value: Int) = setDouble(indices, value.toDouble())
    override fun setShort(indices: IntArray, value: Short) = setDouble(indices, value.toDouble())

    override fun setDouble(i0: Int, i1: Int, value: Double) = set(i0, i1, value)
    fun setDouble(value: Double, i0: Int  , i1: Index) = set(i0.compute(0), i1.compute(1), value)
    fun setDouble(value: Double, i0: Index, i1: Int  ) = set(i0.compute(0), i1.compute(1), value)
    fun setDouble(value: Double, i0: Index, i1: Index) = set(i0.compute(0), i1.compute(1), value)

    override fun setFloat(i0: Int, i1: Int, value: Float) = set(i0, i1, value.toDouble())
    override fun setLong (i0: Int, i1: Int, value: Long) = set(i0, i1, value.toDouble())
    override fun setInt  (i0: Int, i1: Int, value: Int) = set(i0, i1, value.toDouble())
    override fun setShort(i0: Int, i1: Int, value: Short) = set(i0, i1, value.toDouble())

    override fun setValue(i0: Int, i1: Int, value: Double) = set(i0, i1, value)

    override fun setValue(value: ArrayND<Double>) =
            if (value is DoubleArray2D)
                setValue(value)
            else
                super<MutableDoubleArrayND>.setValue(value)

    override fun setValue(value: Array2D<Double>) =
            if (value is DoubleArray2D)
                setValue(value)
            else
                super<MutableDoubleArrayND>.setValue(value)

    fun setValue(value: DoubleArray2D) {

        requireSameShape(this, value)

        value.copy().inlinedForEachIndexed { i0, i1, element ->
            setDouble(i0, i1, element)
        }

    }

    override fun getView(vararg indices: IntProgression): MutableDoubleArray2D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(vararg indices: IndexProgression): MutableDoubleArray2D = getView(*indices.computeIndices())

    override fun lowerRank(axis: Int): MutableDoubleArray1D =
            MutableDoubleArray2DLowerRankView(this, axis)

    override fun arrayAlongAxis(axis: Int, index: Int): MutableDoubleArray1D =
            super<DoubleArray2D>.arrayAlongAxis(axis, index).asMutable()

    override fun getView(i0: Int  , i1: Int  ): MutableDoubleArray0D = defaultDoubleArray2DView(this, i0, i1)
    override fun getView(i0: Int  , i1: Index): MutableDoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Int  ): MutableDoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Index): MutableDoubleArray0D = getView(i0.compute(0), i1.compute(1))

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

    fun setView(value: DoubleArray1D, i0: Int  , i1: IntProgression  ) = getView(i0, i1).setValue(value)
    fun setView(value: DoubleArray1D, i0: Index, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: Int  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: Index, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: DoubleArray1D, i0: IntProgression  , i1: Int  ) = getView(i0, i1).setValue(value)
    fun setView(value: DoubleArray1D, i0: IntProgression  , i1: Index) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: IndexProgression, i1: Int  ) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray1D, i0: IndexProgression, i1: Index) = setView(value, i0.compute(0), i1.compute(1))

    fun setView(value: DoubleArray2D, i0: IntProgression  , i1: IntProgression  ) = getView(i0, i1).setValue(value)
    fun setView(value: DoubleArray2D, i0: IndexProgression, i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray2D, i0: IntProgression  , i1: IndexProgression) = setView(value, i0.compute(0), i1.compute(1))
    fun setView(value: DoubleArray2D, i0: IndexProgression, i1: IntProgression  ) = setView(value, i0.compute(0), i1.compute(1))

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

    override operator fun set(i0: Int, i1: Int, value: Double)
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

}
