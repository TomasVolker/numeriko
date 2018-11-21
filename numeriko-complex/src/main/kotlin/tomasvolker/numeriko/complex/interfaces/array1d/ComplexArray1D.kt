package tomasvolker.numeriko.complex.interfaces.array1d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.array1d.view.*
import tomasvolker.numeriko.complex.primitives.div
import tomasvolker.numeriko.complex.interfaces.array0d.ComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array0d.MutableComplexArray0D
import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.copy
import tomasvolker.numeriko.complex.transforms.fft.array1d.fft
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.numeric.NumericArray1D
import kotlin.math.atan2
import kotlin.math.hypot

interface ComplexArray1D: NumericArray1D<Complex>, ComplexArrayND {

    override fun getValue(vararg indices: Int): Complex {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

    override fun real(vararg indices: Int): Double {
        requireValidIndices(indices)
        return real(indices[0])
    }

    override fun imag(vararg indices: Int): Double {
        requireValidIndices(indices)
        return imag(indices[0])
    }

    override fun getView(i0: IntProgression): ComplexArray1D =
            DefaultComplexArray1DView(
                    array = this.asMutable(),
                    offset = i0.first,
                    size = i0.count(),
                    stride = i0.step
            )

    override fun getView(i0: IndexProgression): ComplexArray1D = getView(i0.compute())

    override fun getView(i0: Int): MutableComplexArray0D =
            defaultComplexArray0DView(this.asMutable(), i0)

    override fun getView(vararg indices: IntProgression): ComplexArray1D {
        requireValidIndices(indices)
        return getView(indices[0])
    }

    override fun lowerRank(axis: Int): ComplexArray0D {
        requireValidAxis(axis)
        return DefaultComplexArray1DLowerRankView(this.asMutable(), 0)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): ComplexArray0D {
        requireValidAxis(axis)
        requireValidIndices(index)
        return DefaultComplexArray1DLowerRankView(this.asMutable(), index)
    }

    operator fun get(index: Int): Complex = getValue(index)
    operator fun get(index: Index): Complex = getValue(index)

    operator fun get(index: IntProgression): ComplexArray1D = getView(index)
    operator fun get(index: IndexProgression): ComplexArray1D = getView(index)

    override fun getValue(i0: Int): Complex = Complex(real(i0), imag(i0))

    override fun copy(): ComplexArray1D = copy(this)

    fun real(i0: Int): Double
    fun imag(i0: Int): Double

    fun abs(i0: Int): Double = hypot(real(i0), imag(i0))
    fun arg(i0: Int): Double = atan2(imag(i0), real(i0))

    val real: DoubleArray1D get() = ComplexArray1DRealView(this.asMutable())
    val imag: DoubleArray1D get() = ComplexArray1DImagView(this.asMutable())

    val abs: DoubleArray1D get() = ComplexArray1DAbsView(this.asMutable())
    val arg: DoubleArray1D get() = ComplexArray1DArgView(this.asMutable())

    override fun asMutable(): MutableComplexArray1D = this as MutableComplexArray1D

    fun conjugate(): ComplexArray1D =
            complexArray1D(
                    size = size,
                    initReal = { i -> real(i) },
                    initImag = { i -> -imag(i) }
            )

    /**
     * Returns this array unaltered.
     */
    override operator fun unaryPlus(): ComplexArray1D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    override operator fun unaryMinus(): ComplexArray1D =
            elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: ComplexArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: ComplexArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: ComplexArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: ComplexArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: ComplexArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: DoubleArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: DoubleArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: DoubleArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: DoubleArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: DoubleArray1D): ComplexArray1D =
            elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Complex): ComplexArray1D =
            elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Complex): ComplexArray1D =
            elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Complex): ComplexArray1D =
            elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Complex): ComplexArray1D =
            elementWise { it / other }

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Complex): ComplexArray1D =
            elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Double): ComplexArray1D =
            elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Double): ComplexArray1D =
            elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Double): ComplexArray1D =
            elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Double): ComplexArray1D =
            elementWise { it / other }

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Double): ComplexArray1D =
            elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    override operator fun plus(other: Int): ComplexArray1D =
            plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    override operator fun minus(other: Int): ComplexArray1D =
            minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    override operator fun times(other: Int): ComplexArray1D =
            times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    override operator fun div(other: Int): ComplexArray1D =
            div(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Int): ComplexArray1D =
            elementWise { other / it }

    fun fastFourierTransform(inverse: Boolean = false): ComplexArray1D =
            fft(this, inverse = inverse)

}
