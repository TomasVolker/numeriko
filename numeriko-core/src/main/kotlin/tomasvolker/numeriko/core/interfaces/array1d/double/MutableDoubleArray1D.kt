package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleArray1DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleReshapedView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.MutableNumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.view.ElementOrder

private const val rankError = "Array is known to be rank 1D in compile time"

interface MutableDoubleArray1D: DoubleArray1D, MutableNumericArray1D<Double>, MutableDoubleArrayND {

    override fun setValue(indices: IntArray, value: Double): Unit =
            setDouble(indices, value)


    override fun setDouble(indices: IntArray, value: Double) {
        requireValidIndices(indices)
        setDouble(indices[0], value)
    }

    private fun rankError(): Nothing = throw IllegalArgumentException("Array is known to be rank 1D in compile time")

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun set(value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override operator fun set(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, value: Double): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun set(vararg indices: Int, value: Double): Nothing = rankError()

    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as0D(): Nothing = rankError()
    @CompileTimeError(message = rankError, level = Level.ERROR)
    override fun as2D(): Nothing = rankError()


    override fun setFloat(indices: IntArray, value: Float) = setDouble(indices, value.toDouble())
    override fun setLong (indices: IntArray, value: Long) = setDouble(indices, value.toDouble())
    override fun setInt  (indices: IntArray, value: Int) = setDouble(indices, value.toDouble())
    override fun setShort(indices: IntArray, value: Short) = setDouble(indices, value.toDouble())

    override fun setValue(i0: Int, value: Double): Unit = setDouble(i0, value)

    override fun setDouble(i0: Int, value: Double) = set(i0, value)
    override fun setFloat (i0: Int, value: Float) = setDouble(i0, value.toDouble())
    override fun setLong  (i0: Int, value: Long) = setDouble(i0, value.toDouble())
    override fun setInt   (i0: Int, value: Int) = setDouble(i0, value.toDouble())
    override fun setShort (i0: Int, value: Short) = setDouble(i0, value.toDouble())

    fun setDouble(i0: Index, value: Double) = setDouble(i0.computeValue(size), value)

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
            setDouble(i, copy.getDouble(i))
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

    override operator fun set(i0: Int, value: Double)
    operator fun set(i0: Index, value: Double): Unit = setValue(value, i0)

    operator fun set(i0: IntProgression  , value: DoubleArray1D): Unit = setView(value, i0)
    operator fun set(i0: IndexProgression, value: DoubleArray1D): Unit = setView(value, i0)

    override fun copy(): MutableDoubleArray1D = copy(this).asMutable()

}
