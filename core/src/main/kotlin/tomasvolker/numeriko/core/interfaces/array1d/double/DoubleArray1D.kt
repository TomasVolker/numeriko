package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultMutableDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array1d.generic.isNotEmpty
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.abs
import kotlin.math.sqrt

interface DoubleArray1D: Array1D<Double>, DoubleArrayND {

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0])
    }

    override fun getValue(i0: Int): Double = getDouble(i0)

    fun getDouble(index: Int): Double
    fun getDouble(index: Index): Double = getDouble(index.compute())

    override fun getView(indexRange: IntProgression): DoubleArray1D =
            DefaultMutableDoubleArray1DView(
                    array = this.asMutable(),
                    offset = indexRange.first,
                    size = indexRange.count(),
                    stride = indexRange.step
            )
    override fun getView(indexRange: IndexProgression): DoubleArray1D = getView(indexRange.compute())


    operator fun get(index: Int): Double = getDouble(index)
    operator fun get(index: Index): Double = getDouble(index)

    operator fun get(index: IntProgression): DoubleArray1D = getView(index)
    operator fun get(index: IndexProgression): DoubleArray1D = getView(index)


    override fun copy(): DoubleArray1D = copy(this)

    override fun asMutable(): MutableDoubleArray1D = this as MutableDoubleArray1D

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray1DIterator(this)


    /**
     * Returns this array unaltered.
     */
    operator fun unaryPlus(): DoubleArray1D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    operator fun unaryMinus(): DoubleArray1D =
            elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
     fun rdiv(other: DoubleArray1D): DoubleArray1D =
            elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Double): DoubleArray1D =
            elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Double): DoubleArray1D =
            elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Double): DoubleArray1D =
            elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Double): DoubleArray1D =
            elementWise { it / other }

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Double): DoubleArray1D =
            elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Int): DoubleArray1D =
            plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Int): DoubleArray1D =
            minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Int): DoubleArray1D =
            times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Int): DoubleArray1D =
            div(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    fun rdiv(other: Int): DoubleArray1D =
            elementWise { other / it }

    /**
     * Computes the sum of all its elements
     */
    fun sum(): Double = sumBy { it }

    /**
     * Computes the average of all its elements
     */
    fun DoubleArray1D.average(): Double = sum() / size

    /**
     * Computes the norm one.
     *
     * This consists of adding the absolute values.
     */
    fun norm1(): Double = sumBy { abs(it) }

    /**
     * Computes the euclidean norm.
     *
     * This consists of adding the squares of the values and apply the square root.
     */
    fun norm2(): Double = sqrt(sumBy { it * it })

    /**
     * Computes the max norm, a.k.a. infinity norm.
     *
     * The max norm is the maximum absolute value of the array. It is also known as the
     * infinity norm as it is equivalent to [norm(p)] when p tends to infinity.
     */
    fun maxNorm(): Double {
        var result = 0.0
        forEachIndex { i ->
            val new = abs(this[i])
            if (result < new)
                result = new
        }
        return result
    }

    /**
     * Computes a normalized vector on norm 2.
     *
     * This consists of dividing this vector by its norm 2.
     */
    fun normalized(): DoubleArray1D = this / this.norm2()

    /**
     * Computes the inner product.
     */
    infix fun inner(other: DoubleArray1D): Double {
        requireSameSize(this, other)
        return sumDouble(indices) { i -> this[i] * other[i] }
    }

    /**
     * Computes the outer product.
     */
    infix fun outer(other: DoubleArray1D): DoubleArray2D {
        requireSameSize(this, other)
        return doubleArray2D(this.size, other.size) { i0, i1 ->
            this[i0] * other[i1]
        }
    }

    /**
     * Computes the cumulative sum.
     *
     * Each value of the result is equal to the sum of those with lower or equal index.
     * It is defined in such a way that `this.cumulativeSum().differences()` is (numerically) equal to `this`.
     * The array returned has the same size.
     */
    fun cumulativeSum(): DoubleArray1D {
        val result = doubleZeros(this.size).asMutable()
        if (this.isNotEmpty()) {
            result[0] = this[0]
        }
        for (i in 1 until size) {
            result[i] = this[i] + result[i -1]
        }
        return result
    }

    /**
     * Computes the differences between subsequent samples (this(i) - this(i-1)).
     *
     * The array returned has the same size, setting the first element this values first element (result(0) = this(0))
     * It is defined in such a way that `this.cumulativeSum().differences()` is (numerically) equal to `this`.
     *
     */
    fun differences(): DoubleArray1D {
        val result = doubleZeros(this.size).asMutable()
        if (this.isNotEmpty()) {
            result[0] = this[0]
        }
        for (i in 1 until size) {
            result[i] = this[i] - this[i -1]
        }
        return result
    }

    /**
     * Computes the circular convolution of the arrays.
     *
     * The arrays must be of the same size. The definition used mirrors [other] to comply with the
     * mathematical definition. To avoid the mirroring use [filter].
     *
     * @throws IllegalAccessException  if [other] is not the same size as [this]
     */
    infix fun convolve(other: DoubleArray1D): DoubleArray1D {
        requireSameSize(this, other)

        return doubleArray1D(this.size) { i ->
            sumDouble(other.indices) { j ->
                this[(i - j) modulo size] * other[j]
            }
        }
    }

    /**
     * Filters this array convolving with the given filter.
     *
     * This implementation does not mirror the filter and uses the [padding] value to extend the array
     * and output an array of the same size.
     * For circular convolution see [convolve].
     *
     * @param filter  the filter to use to convolve with this array, it will not be mirrored
     * @param padding  the value to use on the borders on the array, 0.0 by default
     * @return the filtered array
     */
    fun filter1D(filter: DoubleArray1D, padding: Double = 0.0): DoubleArray1D {

        val filterCenter = filter.size / 2

        return doubleArray1D(this.size) { i ->
            sumDouble(filter.indices) { j ->
                val k = i - j - filterCenter
                if (k in 0 until this.size) {
                    this[k] * filter[j]
                } else {
                    padding
                }
            }
        }
    }

}
