package numeriko.lowrank.functions

import numeriko.lowrank.interfaces.array0d.double.DoubleArray0D
import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import numeriko.lowrank.interfaces.array2d.double.*
import numeriko.lowrank.interfaces.array2d.double.view.DefaultMutableDoubleArray2DTransposeView
import numeriko.lowrank.interfaces.array2d.generic.forEachIndex
import numeriko.lowrank.interfaces.array2d.generic.indices1
import numeriko.lowrank.interfaces.array2d.generic.isSquare
import tomasvolker.core.interfaces.factory.copy
import tomasvolker.core.primitives.indicator


/**
 * Returns a transposed view of this array.
 *
 * The array returned is a view, if a copy is needed call [copy] on the view
 *
 * @return a transposed view of this array
 */
fun DoubleArray2D.transpose(): DoubleArray2D =
        DefaultMutableDoubleArray2DTransposeView(this.asMutable())

fun DoubleArray2D.contract(axis0: Int, axis1: Int): DoubleArray0D =
        if (axis0 == 0 && axis1 == 1 || axis0 == 1 && axis1 == 0)
            doubleArray0D(trace())
        else
            throw IllegalArgumentException()

fun DoubleArray2D.isIdentity(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
        isSquare() && numericEquals(tolerance) { i0, i1 -> (i0 == i1).indicator() }

fun DoubleArray2D.isZero(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
        isConstant(0.0, tolerance)

fun DoubleArray2D.isConstant(value: Double, tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
        numericEquals(tolerance) { _, _ -> value }

fun DoubleArray2D.isSymmetric(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
    if (!isSquare()) return false

    for (i0 in 0 until shape0-1) {
        for (i1 in i0+1 until shape1) {

            if (!this[i0, i1].numericEqualsTo(this[i1, i0], tolerance))
                return false

        }
    }

    return true
}

fun DoubleArray2D.isDiagonal(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
        isLowerTriangular(tolerance) && isUpperTriangular(tolerance)

fun DoubleArray2D.isLowerTriangular(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
    if (!isSquare()) return false

    for (i0 in 0 until shape0-1) {
        for (i1 in i0+1 until shape1) {

            if (!this[i0, i1].numericEqualsTo(0.0, tolerance))
                return false

        }
    }

    return true
}

fun DoubleArray2D.isUpperTriangular(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
    if (!isSquare()) return false

    for (i0 in 1 until shape0) {
        for (i1 in 0 until i0) {

            if (!this[i0, i1].numericEqualsTo(0.0, tolerance))
                return false

        }
    }

    return true
}

fun DoubleArray2D.trace(): Double {
    require(isSquare())
    return sumDouble(0 until this.shape0) { i ->
        this[i, i]
    }
}

fun DoubleArray2D.diagonal(offset: Int = 0): DoubleArray1D {
    require(isSquare())
    // View? non square matrices?
    return doubleArray1D(shape0 - abs(offset)) { i ->
        this[i + offset, i + offset]
    }
}

fun DoubleArray2D.frobeniusNorm(): Double {
    var normSquared = 0.0
    forEachIndex { i0, i1 ->
        normSquared += this[i0, i1].squared()
    }
    return sqrt(normSquared)
}

/**
 * Computes the max norm, a.k.a. infinity norm.
 *
 * The max norm is the maximum absolute value of the array. It is also known as the
 * infinity norm as it is equivalent to `norm(p)` when `p` tends to infinity.
 */
fun DoubleArray2D.maxNorm(): Double {
    var result = 0.0
    forEachIndex { i0, i1 ->
        val new = abs(this[i0, i1])
        if (result < new)
            result = new
    }
    return result
}

/**
 * Computes the sum of all its elements.
 */
fun DoubleArray2D.sum(): Double = sumBy { it }

/**
 * Computes the average of all its elements.
 */
fun DoubleArray2D.average(): Double = sum() / size

fun DoubleArray2D.determinant(): Double = DefaultLinearAlgebra.determinant(this)

/**
 * Solves the linear system determined by this array and the given [image].
 *
 * This is equivalent to solving `this * result = image` in matrix notation with [other] as column vector.
 *
 * @param image  the image array of the transformation to invert
 * @return the result of solving the linear system
 * @throws IllegalArgumentException  if `this.shape0 != image.size`
 */
fun DoubleArray2D.solve(image: DoubleArray1D): DoubleArray1D = DefaultLinearAlgebra.solve(this, image)

/**
 * Computes the matrix inverse of this array.
 *
 * @return the matrix inverse of this array
 * @throws IllegalArgumentException  if this array is not square
 */
fun DoubleArray2D.inverse(): DoubleArray2D = DefaultLinearAlgebra.inverse(this)

fun DoubleArray2D.rightPseudoInverse(): DoubleArray2D = (this.transpose() matMul this).inverse() matMul this.transpose()
fun DoubleArray2D.leftPseudoInverse(): DoubleArray2D = this.transpose() matMul (this.transpose() matMul this).inverse()

/**
 * Computes the matrix multiplication between this and [other].
 *
 * This is equivalent to `this * other` in matrix notation with [other] as column vector.
 *
 * @param other  array to compute the matrix multiplication with
 * @return the result of the matrix multiplication
 * @throws IllegalArgumentException  if `this.shape1 != other.size`
 */
infix fun DoubleArray2D.matMul(other: DoubleArray1D): DoubleArray1D {
    require(this.shape1 == other.size) {
        "sizes dont match: this.shape0 = ${this.shape1} other.size = ${other.size}"
    }
    val contractionIndices = this.indices1

    return doubleArray1D(this.shape0) { i0 ->
        sumDouble(contractionIndices) { k -> this[i0, k] * other[k] }
    }
}

/**
 * Computes the quadratic form of [other] corresponding to this array.
 *
 * This is equivalent to `otherT * this * other` in matrix notation with [other] as column vector.
 *
 * @param other  first array to compute the quadratic form
 * @return the result of the quadratic form
 * @throws IllegalArgumentException  if `this.shape0 != other.size` or `this.shape1 != other.size`
 */
fun DoubleArray2D.quadraticForm(other: DoubleArray1D): Double =
        bilinearForm(other, other)

/**
 * Computes the bilinear form between [other1] and [other2] corresponding to this array.
 *
 * This is equivalent to `other1T * this * other2` in matrix notation with [other1] and [other2]
 * as column vectors.
 *
 * @param other1  first array to compute the bilinear form
 * @param other2  second array to compute the bilinear form
 * @return the result of the bilinear form
 * @throws IllegalArgumentException  if `this.shape0 != other0.size` or `this.shape1 != other1.size`
 */
fun DoubleArray2D.bilinearForm(other0: DoubleArray1D, other1: DoubleArray1D): Double {
    require(this.shape0 == other0.size && this.shape1 == other1.size) {
        "Sizes dont match"
    }

    var result = 0.0
    forEachIndex { i0, i1 ->
        result += this[i0, i1] * other0[i0] * other1[i1]
    }
    return result
}

/**
 * Computes the matrix multiplication between this and [other]
 *
 * This is equivalent to `this * other` in matrix notation with [other] as a column vector
 *
 * @param other  array to compute the matrix multiplication with
 * @return the result of the matrix multiplication
 * @throws IllegalArgumentException  if `this.shape1 != other.shape0`
 */
infix fun DoubleArray2D.matMul(other: DoubleArray2D): DoubleArray2D {
    require(this.shape1 == other.shape0) {
        "sizes dont match"
    }
    return doubleArray2D(this.shape0, other.shape1) { i0, i1 ->
        sumDouble(this.indices1) { k -> this[i0, k] * other[k, i1] }
    }
}

fun DoubleArray2D.filter2D(filter: DoubleArray2D, padding: Double = 0.0): DoubleArray2D {

    val filterCenter0 = filter.shape0 / 2
    val filterCenter1 = filter.shape1 / 2

    val filterShape0 = filter.shape0
    val filterShape1 = filter.shape1

    val resultShape0 = this.shape0
    val resultShape1 = this.shape1

    return doubleArray2D(resultShape0, resultShape1) { i0, i1 ->
        sumDouble(0 until filterShape0, 0 until filterShape1) { j0, j1 ->
            val k0 = i0 + j0 - filterCenter0
            val k1 = i1 + j1 - filterCenter1
            if (k0 in 0 until resultShape0 && k1 in 0 until resultShape1) {
                this[k0, k1]
            } else {
                padding
            } * filter[j0, j1]
        }
    }
}


fun MutableDoubleArray2D.applyPlus (other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t + o }
fun MutableDoubleArray2D.applyMinus(other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t - o }
fun MutableDoubleArray2D.applyTimes(other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t * o }
fun MutableDoubleArray2D.applyDiv  (other: DoubleArray2D): MutableDoubleArray2D = applyElementWise(other) { t, o -> t / o }

fun MutableDoubleArray2D.applyPlus (other: Double): MutableDoubleArray2D = applyElementWise { it + other }
fun MutableDoubleArray2D.applyMinus(other: Double): MutableDoubleArray2D = applyElementWise { it - other }
fun MutableDoubleArray2D.applyTimes(other: Double): MutableDoubleArray2D = applyElementWise { it * other }
fun MutableDoubleArray2D.applyDiv  (other: Double): MutableDoubleArray2D = applyElementWise { it / other }

fun MutableDoubleArray2D.applyPlus (other: Int): MutableDoubleArray2D = applyPlus(other.toDouble())
fun MutableDoubleArray2D.applyMinus(other: Int): MutableDoubleArray2D = applyMinus(other.toDouble())
fun MutableDoubleArray2D.applyTimes(other: Int): MutableDoubleArray2D = applyTimes(other.toDouble())
fun MutableDoubleArray2D.applyDiv  (other: Int): MutableDoubleArray2D = applyDiv(other.toDouble())