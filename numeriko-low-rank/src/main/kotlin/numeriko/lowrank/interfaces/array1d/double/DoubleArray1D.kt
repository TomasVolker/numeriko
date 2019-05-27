package numeriko.lowrank.interfaces.array1d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.core.functions.matMul

import tomasvolker.core.index.Index
import tomasvolker.core.index.IndexProgression
import numeriko.lowrank.interfaces.array0d.double.DoubleArray0D
import numeriko.lowrank.interfaces.array1d.double.view.DefaultDoubleArray1DHigherRankView
import numeriko.lowrank.interfaces.array1d.double.view.DefaultDoubleReshapedView
import numeriko.lowrank.interfaces.array1d.double.view.defaultDoubleArray0DView
import numeriko.lowrank.interfaces.array1d.double.view.defaultDoubleArray1DView
import numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import numeriko.lowrank.interfaces.array1d.numeric.NumericArray1D
import tomasvolker.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.core.interfaces.arraynd.integer.MutableDoubleArrayND
import tomasvolker.core.preconditions.requireValidAxis
import tomasvolker.core.preconditions.requireValidIndices
import tomasvolker.core.interfaces.factory.copy
import tomasvolker.core.interfaces.factory.intArray1DOf
import tomasvolker.core.preconditions.rankError
import tomasvolker.core.preconditions.rankError1DMessage
import tomasvolker.core.view.ElementOrder

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

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun get(): Nothing = rankError(0)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int): Nothing = rankError(2)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(3)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(4)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(5)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(6)

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun get(vararg indices: Int): Nothing = rankError(-1)

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun as0D(): Nothing = rankError(0)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError1DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

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

    /**
     * Returns this array unaltered.
     */
    override operator fun unaryPlus(): DoubleArray1D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    override operator fun unaryMinus(): DoubleArray1D = elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: DoubleArray1D): DoubleArray1D = elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: DoubleArray1D): DoubleArray1D = elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: DoubleArray1D): DoubleArray1D = elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: DoubleArray1D): DoubleArray1D = elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: DoubleArray1D): DoubleArray1D = elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Double): DoubleArray1D = elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Double): DoubleArray1D = elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Double): DoubleArray1D = elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Double): DoubleArray1D = elementWise { it / other }

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Double): DoubleArray1D = elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Int): DoubleArray1D = plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Int): DoubleArray1D = minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Int): DoubleArray1D = times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Int): DoubleArray1D = div(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Int): DoubleArray1D = elementWise { other / it }

}