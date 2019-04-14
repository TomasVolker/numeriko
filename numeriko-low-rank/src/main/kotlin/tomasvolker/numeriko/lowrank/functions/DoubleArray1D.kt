package tomasvolker.numeriko.lowrank.functions

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.*
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.isNotEmpty
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D

/**
 * Computes the sum of all its elements
 */
fun DoubleArray1D.sum(): Double = sumBy { it }

/**
 * Computes the average of all its elements
 */
fun DoubleArray1D.average(): Double = sum() / size

/**
 * Computes the norm one.
 *
 * This consists of adding the absolute values.
 */
fun DoubleArray1D.norm1(): Double = sumBy { abs(it) }

/**
 * Computes the euclidean norm.
 *
 * This consists of adding the squares of the values and apply the square root.
 */
fun DoubleArray1D.norm2(): Double = sqrt(sumBy { it * it })

/**
 * Computes the max norm, a.k.a. infinity norm.
 *
 * The max norm is the maximum absolute value of the array. It is also known as the
 * infinity norm as it is equivalent to [norm(p)] when p tends to infinity.
 */
fun DoubleArray1D.maxNorm(): Double {
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
fun DoubleArray1D.normalized(): DoubleArray1D = this / this.norm2()

/**
 * Computes the inner product.
 */
infix fun DoubleArray1D.inner(other: DoubleArray1D): Double {
    requireSameSize(this, other)
    return sumDouble(indices) { i -> this[i] * other[i] }
}

/**
 * Computes the outer product.
 */
infix fun DoubleArray1D.outer(other: DoubleArray1D): DoubleArray2D {
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
infix fun DoubleArray1D.matMul(other: DoubleArray2D): DoubleArray1D {
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
fun DoubleArray1D.cumulativeSum(): DoubleArray1D {
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
fun DoubleArray1D.differences(): DoubleArray1D {
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
infix fun DoubleArray1D.convolve(other: DoubleArray1D): DoubleArray1D {
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
fun DoubleArray1D.filter1D(filter: DoubleArray1D, padding: Double = 0.0): DoubleArray1D {

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


fun MutableDoubleArray1D.applyPlus (other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t + o }
fun MutableDoubleArray1D.applyMinus(other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t - o }
fun MutableDoubleArray1D.applyTimes(other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t * o }
fun MutableDoubleArray1D.applyDiv  (other: DoubleArray1D): MutableDoubleArray1D = applyElementWise(other) { t, o -> t / o }

fun MutableDoubleArray1D.applyPlus (other: Double): MutableDoubleArray1D = applyElementWise { it + other }
fun MutableDoubleArray1D.applyMinus(other: Double): MutableDoubleArray1D = applyElementWise { it - other }
fun MutableDoubleArray1D.applyTimes(other: Double): MutableDoubleArray1D = applyElementWise { it * other }
fun MutableDoubleArray1D.applyDiv  (other: Double): MutableDoubleArray1D = applyElementWise { it / other }

fun MutableDoubleArray1D.applyPlus (other: Int): MutableDoubleArray1D = applyPlus(other.toDouble())
fun MutableDoubleArray1D.applyMinus(other: Int): MutableDoubleArray1D = applyMinus(other.toDouble())
fun MutableDoubleArray1D.applyTimes(other: Int): MutableDoubleArray1D = applyTimes(other.toDouble())
fun MutableDoubleArray1D.applyDiv  (other: Int): MutableDoubleArray1D = applyDiv(other.toDouble())
