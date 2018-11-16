package tomasvolker.numeriko.complex.array

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.array.view.*
import tomasvolker.numeriko.complex.div
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.complex.transforms.fft.array1d.fft
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameSize
import kotlin.math.atan2
import kotlin.math.hypot

interface ComplexArray1D: Array1D<Complex> {

    override fun getView(indexRange: IntProgression): ComplexArray1D =
            DefaultMutableComplexArray1DView(
                    array = this.asMutable(),
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )

    override fun getView(indexRange: IndexProgression): ComplexArray1D =
            getView(indexRange.computeProgression(size))

    operator fun get(index: Int): Complex = getValue(index)
    operator fun get(index: Index): Complex = getValue(index)

    operator fun get(index: IntProgression): ComplexArray1D = getView(index)
    operator fun get(index: IndexProgression): ComplexArray1D = getView(index)

    override fun getValue(index: Int): Complex =
            Complex(getReal(index), getImag(index))

    fun getReal(i: Int): Double
    fun getImag(i: Int): Double

    fun getAbs(i: Int): Double = hypot(getReal(i), getImag(i))
    fun getArg(i: Int): Double = atan2(getImag(i), getReal(i))

    val real: DoubleArray1D get() = ComplexArray1DRealView(this.asMutable())
    val imag: DoubleArray1D get() = ComplexArray1DImagView(this.asMutable())

    val abs: DoubleArray1D get() = ComplexArray1DAbsView(this.asMutable())
    val arg: DoubleArray1D get() = ComplexArray1DArgView(this.asMutable())

    override fun asMutable(): MutableComplexArray1D = this as MutableComplexArray1D

    fun conjugate(): ComplexArray1D =
            complexArray1D(
                    size = size,
                    initReal = { i -> getReal(i) },
                    initImag = { i -> -getImag(i) }
            )

    /**
     * Returns this array unaltered.
     */
    operator fun unaryPlus(): ComplexArray1D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    operator fun unaryMinus(): ComplexArray1D =
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
    operator fun plus(other: Double): ComplexArray1D =
            elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Double): ComplexArray1D =
            elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Double): ComplexArray1D =
            elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Double): ComplexArray1D =
            elementWise { it / other }

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Double): ComplexArray1D =
            elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Int): ComplexArray1D =
            plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Int): ComplexArray1D =
            minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Int): ComplexArray1D =
            times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Int): ComplexArray1D =
            div(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Int): ComplexArray1D =
            elementWise { other / it }

    fun fastFourierTransform(inverse: Boolean = false): ComplexArray1D =
            fft(this, inverse = inverse)

}

fun DoubleArray1D.toComplexArray(): ComplexArray1D =
        complexArray1D(size) { i ->
            this[i].toComplex()
        }

inline fun ComplexArray1D.elementWiseIndexed(
        operation: (i: Int, Complex)->Complex
): ComplexArray1D {
    val result = complexZeros(size).asMutable()
    for (i in indices) {
        result[i] = operation(i, this[i])
    }
    return result
}

inline fun ComplexArray1D.elementWise(
        operation: (Complex)->Complex
): ComplexArray1D {
    val result = complexZeros(size).asMutable()
    for (i in indices) {
        result[i] = operation(this[i])
    }
    return result
}

inline fun elementWise(
        array1: ComplexArray1D,
        array2: ComplexArray1D,
        operation: (Complex, Complex)->Complex
): ComplexArray1D {
    requireSameSize(array1, array2)
    val result = complexZeros(array1.size).asMutable()
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result
}

inline fun elementWise(
        array1: ComplexArray1D,
        array2: DoubleArray1D,
        operation: (Complex, Double)->Complex
): ComplexArray1D {
    requireSameSize(array1, array2)
    val result = complexZeros(array1.size).asMutable()
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result
}

fun complexZeros(size: Int): ComplexArray1D =
        JvmComplexArray(values = doubleZeros(size, 2).asMutable())

fun complexArray1DOf(vararg complex: Complex): ComplexArray1D =
        complexArray1D(complex)

fun complexArray1D(complex: Array<out Complex>): ComplexArray1D =
        JvmComplexArray(
                values = doubleArray2D(complex.size, 2) { i0, i1 ->
                    when(i1) {
                        0 -> complex[i0].real
                        1 -> complex[i0].imag
                        else -> throw IllegalStateException()
                    }
                }.asMutable()
        )

inline fun complexArray1D(size: Int, init: (i: Int)->Complex): ComplexArray1D {
    val values = doubleZeros(size, 2).asMutable()

    for (i in 0 until size) {
        val complex = init(i)
        values[i, 0] = complex.real
        values[i, 1] = complex.imag
    }

    return JvmComplexArray(
            values = values
    )
}

inline fun complexArray1D(
        size: Int,
        initReal: (i: Int)->Double,
        initImag: (i: Int)->Double
): ComplexArray1D {
    val values = doubleZeros(size, 2).asMutable()

    for (i in 0 until size) {
        values[size, 0] = initReal(i)
        values[size, 1] = initImag(i)
    }

    return JvmComplexArray(
            values = values
    )
}


class JvmComplexArray(
        val values: MutableDoubleArray2D
): DefaultMutableComplexArray1D() {

    override val size: Int
        get() = values.shape0

    override val real: DoubleArray1D
        get() = values[All, 0]

    override val imag: DoubleArray1D
        get() = values[All, 1]

    override fun getReal(i: Int): Double = values[i, 0]
    override fun getImag(i: Int): Double = values[i, 1]

    override fun setReal(value: Double, i: Int) {
        values[i, 0] = value
    }

    override fun setImag(value: Double, i: Int) {
        values[i, 1] = value
    }

}