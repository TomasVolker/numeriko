package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleArray1DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleReshapedView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.NumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError1DMessage
import tomasvolker.numeriko.core.view.ElementOrder

interface DoubleArray1D: NumericArray1D<Double>, DoubleArrayND {

    override fun getView(vararg indices: IntProgression): DoubleArray1D {
        requireValidIndices(indices)
        return getView(indices[0])
    }

    override operator fun get(i0: Int): Double

    override fun getDouble(i0: Int): Double = get(i0)
    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getDouble(indices: IntArray): Double {
        requireValidIndices(indices)
        return getDouble(indices[0])
    }

    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override fun get(): Nothing = rankError(0, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError(2, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(3, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(4, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(5, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(6, 1)

    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override fun get(vararg indices: Int): Nothing = rankError(-1, 1)

    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override fun as0D(): Nothing = rankError(0, 1)
    @CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2, 1)

    override fun as1D() = this

    override fun getValue(i0: Int): Double = getDouble(i0)
    override fun getFloat(indices: IntArray): Float = getDouble(indices).toFloat()
    override fun getLong (indices: IntArray): Long  = getDouble(indices).toLong()

    override fun getInt  (indices: IntArray): Int   = getDouble(indices).toInt()
    override fun getShort(indices: IntArray): Short = getDouble(indices).toShort()
    override fun getFloat(i0: Int): Float = getDouble(i0).toFloat()
    override fun getLong (i0: Int): Long  = getDouble(i0).toLong()
    override fun getInt  (i0: Int): Int   = getDouble(i0).toInt()

    override fun getShort(i0: Int): Short = getDouble(i0).toShort()

    fun getDouble(i0: Index): Double = getDouble(i0.compute())

    override fun withShape(shape0: Int, shape1: Int, order: ElementOrder): DoubleArray2D =
            withShape(intArray1DOf(shape0, shape1), order).as2D()

    override fun withShape(shape: IntArray1D, order: ElementOrder): MutableDoubleArrayND =
            DefaultDoubleReshapedView(
                    shape = shape.copy(),
                    array = this.asMutable(),
                    offset = 0,
                    order = order
            )

    override fun lowerRank(axis: Int): DoubleArray0D {
        requireValidAxis(axis)
        return defaultDoubleArray0DView(this.asMutable(), 0)
    }

    override fun higherRank(axis: Int): DoubleArray2D {
        requireValidAxis(axis)
        return DefaultDoubleArray1DHigherRankView(this.asMutable(), axis)
    }
    override fun getView(i0: Int  ): DoubleArray0D = defaultDoubleArray0DView(this.asMutable(), i0)

    override fun getView(i0: Index): DoubleArray0D = getView(i0.compute())
    override fun getView(i0: IntProgression): DoubleArray1D = defaultDoubleArray1DView(this.asMutable(), i0)

    override fun getView(i0: IndexProgression): DoubleArray1D = getView(i0.compute())
    operator fun get(i0: Index): Double = getDouble(i0)

    operator fun get(i0: IntProgression): DoubleArray1D = getView(i0)
    operator fun get(i0: IndexProgression): DoubleArray1D = getView(i0)

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArray0D {
        requireValidAxis(axis)
        return getView(index)
    }

    override fun copy(): DoubleArray1D = copy(this)

    override fun asMutable(): MutableDoubleArray1D = this as MutableDoubleArray1D

    override fun iterator(): DoubleIterator = arrayIterator()
    override fun arrayIterator(): DoubleArray1DIterator = DefaultDoubleArray1DIterator(this)

}
