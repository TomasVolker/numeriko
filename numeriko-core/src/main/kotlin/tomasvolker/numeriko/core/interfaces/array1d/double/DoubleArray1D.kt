package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray0DView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.defaultDoubleArray1DView
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.generic.isNotEmpty
import tomasvolker.numeriko.core.interfaces.array1d.numeric.NumericArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.abs
import kotlin.math.sqrt

interface DoubleArray1D: NumericArray1D<Double>, DoubleArrayND {

    override fun getView(vararg indices: IntProgression): DoubleArray1D {
        requireValidIndices(indices)
        return getView(indices[0])
    }

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0])
    }

    override fun getValue(i0: Int): Double = getDouble(i0)

    override fun getFloat(vararg indices: Int): Float = getDouble(*indices).toFloat()
    override fun getLong (vararg indices: Int): Long  = getDouble(*indices).toLong()
    override fun getInt  (vararg indices: Int): Int   = getDouble(*indices).toInt()
    override fun getShort(vararg indices: Int): Short = getDouble(*indices).toShort()

    override fun getDouble(i0: Int): Double
    override fun getFloat(i0: Int): Float = getDouble(i0).toFloat()
    override fun getLong (i0: Int): Long  = getDouble(i0).toLong()
    override fun getInt  (i0: Int): Int   = getDouble(i0).toInt()
    override fun getShort(i0: Int): Short = getDouble(i0).toShort()

    fun getDouble(i0: Index): Double = getDouble(i0.compute())

    override fun lowerRank(axis: Int): DoubleArray0D {
        requireValidAxis(axis)
        return defaultDoubleArray0DView(this.asMutable(), 0)
    }

    override fun getView(i0: Int  ): DoubleArray0D = defaultDoubleArray0DView(this.asMutable(), i0)
    override fun getView(i0: Index): DoubleArray0D = getView(i0.compute())

    override fun getView(i0: IntProgression): DoubleArray1D = defaultDoubleArray1DView(this.asMutable(), i0)
    override fun getView(i0: IndexProgression): DoubleArray1D = getView(i0.compute())

    operator fun get(i0: Int): Double = getDouble(i0)
    operator fun get(i0: Index): Double = getDouble(i0)

    operator fun get(i0: IntProgression): DoubleArray1D = getView(i0)
    operator fun get(i0: IndexProgression): DoubleArray1D = getView(i0)

    override fun arrayAlongAxis(axis: Int, index: Int): DoubleArray0D {
        requireValidAxis(axis)
        return getView(index)
    }

    override fun copy(): DoubleArray1D = copy(this)

    override fun asMutable(): MutableDoubleArray1D = this as MutableDoubleArray1D

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray1DIterator(this)


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
     * Computes the matrix multiplication between this and [other]
     *
     * This is equivalent to `this^T * other` in matrix notation with [other] as a column vector
     *
     * @param other  array to compute the matrix multiplication with
     * @return the result of the matrix multiplication
     * @throws IllegalArgumentException  if `this.size != other.shape1`
     */
    infix fun matMul(other: DoubleArray2D): DoubleArray1D {
        require(this.size == other.shape0) {
            "sizes dont match"
        }

        val contractionSize = this.size

        return doubleArray1D(other.shape1) { i ->
            sumDouble(0 until contractionSize) { k -> this[k] * other[k, i] }
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

        val resultSize = this.size
        val filterSize = filter.size

        return doubleArray1D(resultSize) { i ->
            sumDouble(0 until filterSize) { j ->
                val k = i + j - filterCenter
                if (k in 0 until resultSize) {
                    this[k]
                } else {
                    padding
                } * filter[j]
            }
        }
    }

}
