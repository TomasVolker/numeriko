package tomasvolker.numeriko.lowrank.interfaces.array2d.double

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.core.functions.matMul
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.view.MutableDoubleArray2DLowerRankView
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.view.defaultDoubleArray2DView
import tomasvolker.numeriko.lowrank.interfaces.array2d.numeric.NumericArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.preconditions.requireValidAxis
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError2DMessage

interface DoubleArray2D: NumericArray2D<Double>, DoubleArrayND {

    override fun cast(value: Number): Double = value.toDouble()

    override fun getDouble(indices: IntArray): Double {
        require(indices.size == rank)
        return get(indices[0], indices[1])
    }

    override fun getFloat(indices: IntArray): Float = getDouble(indices).toFloat()
    override fun getLong (indices: IntArray): Long  = getDouble(indices).toLong()
    override fun getInt  (indices: IntArray): Int   = getDouble(indices).toInt()
    override fun getShort(indices: IntArray): Short = getDouble(indices).toShort()

    override fun getValue(indices: IntArray): Double = getDouble(indices)

    override fun getValue(i0: Int, i1: Int): Double = getDouble(i0, i1)

    override fun lowerRank(axis: Int): DoubleArray1D =
            MutableDoubleArray2DLowerRankView(this.asMutable(), axis)

    override fun getFloat(i0: Int, i1: Int  ): Float = getDouble(i0, i1).toFloat()

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun get(): Nothing = rankError(0)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(i0: Int): Nothing = rankError(1)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int): Nothing = rankError(3)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int): Nothing = rankError(4)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): Nothing = rankError(5)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Nothing = rankError(6)

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override operator fun get(vararg indices: Int): Nothing = rankError(rank)

    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as0D(): Nothing = rankError(0)
    @CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)

    override fun as2D() = this

    override fun get(i0: Int, i1: Int): Double

    override fun getDouble(i0: Int, i1: Int  ): Double = get(i0, i1)
    fun getDouble(i0: Int  , i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Int  ): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArray1D =
            when(axis) {
                0 -> getView(index, All)
                1 -> getView(All, index)
                else -> {
                    requireValidAxis(axis)
                    error("requireValidAxis should fail")
                }
            }

    override fun getView(vararg indices: IntProgression): DoubleArray2D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getView(vararg indices: IndexProgression): DoubleArray2D =
            getView(*indices.computeIndices())

    override fun getView(i0: Int  , i1: Int)  : DoubleArray0D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: Index): DoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Int  ): DoubleArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Index): DoubleArray0D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: Int  , i1: IntProgression): DoubleArray1D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: IndexProgression): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): DoubleArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: Int): DoubleArray1D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: Index): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Int  ): DoubleArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): DoubleArray1D = getView(i0.compute(0), i1.compute(1))


    override fun getView(i0: IntProgression, i1: IntProgression): DoubleArray2D = defaultDoubleArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: IndexProgression): DoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): DoubleArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): DoubleArray2D = getView(i0.compute(0), i1.compute(1))


    operator fun get(i0: Int  , i1: Index): Double = getDouble(i0, i1)
    operator fun get(i0: Index, i1: Int  ): Double = getDouble(i0, i1)
    operator fun get(i0: Index, i1: Index): Double = getDouble(i0, i1)

    operator fun get(i0: Int  , i1: IntProgression  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IntProgression  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Int  , i1: IndexProgression): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IndexProgression): DoubleArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: Int  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: Index): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Int  ): DoubleArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Index): DoubleArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: IntProgression  ): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: IndexProgression): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IntProgression  ): DoubleArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IndexProgression): DoubleArray2D = getView(i0, i1)

    override fun toDoubleArrayND(): DoubleArray2D = this

    override fun copy(): DoubleArray2D = copy(this)

    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

    override fun iterator(): DoubleIterator = arrayIterator()
    override fun arrayIterator(): DoubleArray2DIterator = DefaultDoubleArray2DIterator(this)

    /**
     * Returns this array unaltered.
     */
    override operator fun unaryPlus(): DoubleArray2D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    override operator fun unaryMinus(): DoubleArray2D = elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: DoubleArray2D): DoubleArray2D = elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: DoubleArray2D): DoubleArray2D = elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: DoubleArray2D): DoubleArray2D = elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: DoubleArray2D): DoubleArray2D = elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: DoubleArray2D): DoubleArray2D = elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Double): DoubleArray2D = elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Double): DoubleArray2D = elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Double): DoubleArray2D = elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Double): DoubleArray2D = elementWise { it / other }

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Double): DoubleArray2D = elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Int): DoubleArray2D = plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Int): DoubleArray2D = minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Int): DoubleArray2D = times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Int): DoubleArray2D = div(other.toDouble())

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Int): DoubleArray2D = elementWise { other / it }

}
