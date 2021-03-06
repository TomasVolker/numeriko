package tomasvolker.numeriko.complex.interfaces.array2d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.primitives.div
import tomasvolker.numeriko.complex.interfaces.array0d.ComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.view.*
import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.complex.interfaces.factory.*
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array2d.numeric.NumericArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import kotlin.math.atan2
import kotlin.math.hypot

interface ComplexArray2D: NumericArray2D<Complex>, ComplexArrayND {

    override fun real(vararg indices: Int): Double {
        requireValidIndices(indices)
        return real(indices[0], indices[1])
    }

    override fun imag(vararg indices: Int): Double {
        requireValidIndices(indices)
        return imag(indices[0], indices[1])
    }

    override fun getValue(vararg indices: Int): Complex {
        requireValidIndices(indices)
        return getValue(indices[0], indices[1])
    }

    override fun toDoubleArrayND(): DoubleArray2D = doubleArray2D(shape0, shape1) { i0, i1 -> real(i0, i1) }

    override fun lowerRank(axis: Int): ComplexArray1D {
        requireValidAxis(axis)
        return MutableComplexArray2DLowerRankView(this.asMutable(), axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): ComplexArray1D =
            when(axis) {
                0 -> getView(index, All)
                1 -> getView(All, index)
                else -> {
                    requireValidAxis(axis)
                    error("requireValidAxis should fail")
                }
            }

    override fun getView(vararg indices: IndexProgression): ComplexArray2D =
            getView(*indices.computeIndices())

    override fun getView(vararg indices: IntProgression): ComplexArray2D {
        requireValidIndices(indices)
        return getView(indices[0], indices[1])
    }

    override fun getValue(i0: Int, i1: Int): Complex = Complex(real(i0, i1), imag(i0, i1))

    fun real(i0: Int, i1: Int): Double
    fun imag(i0: Int, i1: Int): Double

    fun abs(i0: Int, i1: Int): Double = hypot(real(i0, i1), imag(i0, i1))
    fun arg(i0: Int, i1: Int): Double = atan2(imag(i0, i1), real(i0, i1))

    val real: DoubleArray2D get() = ComplexArray2DRealView(this.asMutable())
    val imag: DoubleArray2D get() = ComplexArray2DImagView(this.asMutable())

    val abs: DoubleArray2D get() = ComplexArray2DAbsView(this.asMutable())
    val arg: DoubleArray2D get() = ComplexArray2DArgView(this.asMutable())

    override fun getView(i0: Int  , i1: Int  ): ComplexArray0D = defaultComplexArray2DView(this.asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: Index): ComplexArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Int  ): ComplexArray0D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: Index): ComplexArray0D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: Int, i1: IntProgression): ComplexArray1D = defaultComplexArray2DView(asMutable(), i0, i1)
    override fun getView(i0: Int  , i1: IndexProgression): ComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IntProgression  ): ComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: Index, i1: IndexProgression): ComplexArray1D = getView(i0.compute(0), i1.compute(1))

    override fun getView(i0: IntProgression, i1: Int): ComplexArray1D = defaultComplexArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: Index): ComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Int  ): ComplexArray1D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: Index): ComplexArray1D = getView(i0.compute(0), i1.compute(1))


    override fun getView(i0: IntProgression, i1: IntProgression): ComplexArray2D = defaultComplexArray2DView(asMutable(), i0, i1)
    override fun getView(i0: IntProgression  , i1: IndexProgression): ComplexArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IntProgression  ): ComplexArray2D = getView(i0.compute(0), i1.compute(1))
    override fun getView(i0: IndexProgression, i1: IndexProgression): ComplexArray2D = getView(i0.compute(0), i1.compute(1))


    operator fun get(i0: Int  , i1: Int  ): Complex = getValue(i0, i1)
    operator fun get(i0: Int  , i1: Index): Complex = getValue(i0, i1)
    operator fun get(i0: Index, i1: Int  ): Complex = getValue(i0, i1)
    operator fun get(i0: Index, i1: Index): Complex = getValue(i0, i1)

    operator fun get(i0: Int  , i1: IntProgression  ): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IntProgression  ): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: Int  , i1: IndexProgression): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: Index, i1: IndexProgression): ComplexArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: Int  ): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: Index): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Int  ): ComplexArray1D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: Index): ComplexArray1D = getView(i0, i1)

    operator fun get(i0: IntProgression  , i1: IntProgression  ): ComplexArray2D = getView(i0, i1)
    operator fun get(i0: IntProgression  , i1: IndexProgression): ComplexArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IntProgression  ): ComplexArray2D = getView(i0, i1)
    operator fun get(i0: IndexProgression, i1: IndexProgression): ComplexArray2D = getView(i0, i1)


    override fun copy(): ComplexArray2D = copy(this)

    override fun asMutable(): MutableComplexArray2D = this as MutableComplexArray2D

    /**
     * Returns this array unaltered.
     */
    override operator fun unaryPlus(): ComplexArray2D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    override operator fun unaryMinus(): ComplexArray2D = elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: ComplexArray2D): ComplexArray2D = elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: ComplexArray2D): ComplexArray2D = elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: ComplexArray2D): ComplexArray2D = elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: ComplexArray2D): ComplexArray2D = elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: ComplexArray2D): ComplexArray2D = elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Double): ComplexArray2D = elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Double): ComplexArray2D = elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Double): ComplexArray2D = elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Double): ComplexArray2D = elementWise { it / other }

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Double): ComplexArray2D = elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Int): ComplexArray2D = plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Int): ComplexArray2D = minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Int): ComplexArray2D = times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Int): ComplexArray2D = div(other.toDouble())

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Int): ComplexArray2D = elementWise { other / it }

    fun transpose(): ComplexArray2D = DefaultMutableComplexArray2DTransposeView(this.asMutable())

}
