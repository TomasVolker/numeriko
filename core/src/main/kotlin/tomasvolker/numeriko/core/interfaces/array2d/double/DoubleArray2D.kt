package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.*
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.linearalgebra.DefaultLinearAlgebra
import tomasvolker.numeriko.core.primitives.indicative
import tomasvolker.numeriko.core.primitives.numericEqualsTo
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.abs
import kotlin.math.sqrt

interface DoubleArray2D: Array2D<Double>, DoubleArrayND {

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0], indices[1])
    }

    override fun getValue(vararg indices: Int): Double =
            getDouble(*indices)

    override fun getValue(i0: Int, i1: Int): Double =
            getDouble(i0, i1)

    fun getDouble(i0: Int  , i1: Int  ): Double
    fun getDouble(i0: Int  , i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Int  ): Double = getDouble(i0.compute(0), i1.compute(1))
    fun getDouble(i0: Index, i1: Index): Double = getDouble(i0.compute(0), i1.compute(1))

    override fun getView(i0: Int, i1: IntProgression): DoubleArray1D = defaultDoubleArray2DView(asMutable(), i0, i1)
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


    operator fun get(i0: Int  , i1: Int  ): Double = getDouble(i0, i1)
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


    override fun copy(): DoubleArray2D = copy(this)

    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray2DIterator(this)

    /**
     * Returns a transposed view of this array.
     *
     * The array returned is a view, if a copy is needed call [copy] on the view
     *
     * @return a transposed view of this array
     */
    fun transpose(): DoubleArray2D =
            DefaultMutableDoubleArray2DTransposeView(this.asMutable())

    override fun contract(axis0: Int, axis1: Int): DoubleArrayND =
            if (axis0 == 0 && axis1 == 1 || axis0 == 1 && axis1 == 0)
                doubleArrayND(intArray1DOf()) { trace() }
            else
                throw IllegalArgumentException()

    fun isIdentity(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
            isSquare() && numericEquals(tolerance) { i0, i1 -> (i0 == i1).indicative() }

    fun isZero(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
            isConstant(0.0, tolerance)

    fun isConstant(value: Double, tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
            numericEquals(tolerance) { _, _ -> value }

    fun isSymmetric(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
        if (!isSquare()) return false

        for (i0 in 0 until shape0-1) {
            for (i1 in i0+1 until shape1) {

                if (!this[i0, i1].numericEqualsTo(this[i1, i1], tolerance))
                    return false

            }
        }

        return true
    }

    fun isDiagonal(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean =
            isLowerTriangular(tolerance) && isUpperTriangular(tolerance)

    fun isLowerTriangular(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
        if (!isSquare()) return false

        for (i0 in 0 until shape0-1) {
            for (i1 in i0+1 until shape1) {

                if (!this[i0, i1].numericEqualsTo(0.0, tolerance))
                    return false

            }
        }

        return true
    }

    fun isUpperTriangular(tolerance: Double = NumerikoConfig.defaultTolerance): Boolean {
        if (!isSquare()) return false

        for (i0 in 1 until shape0) {
            for (i1 in 0 until i0) {

                if (!this[i0, i1].numericEqualsTo(0.0, tolerance))
                    return false

            }
        }

        return true
    }

    fun trace(): Double {
        require(isSquare())
        return sumDouble(indices0) { i ->
            this[i, i]
        }
    }

    fun diagonal(offset: Int = 0): DoubleArray1D {
        require(isSquare())
        // View? non square matrices?
        return doubleArray1D(shape0 - abs(offset)) { i ->
            this[i + offset, i + offset]
        }
    }

    fun frobeniusNorm(): Double {
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
     * infinity norm as it is equivalent to [norm(p)] when p tends to infinity.
     */
    fun maxNorm(): Double {
        var result = 0.0
        forEachIndex { i0, i1 ->
            val new = abs(this[i0, i1])
            if (result < new)
                result = new
        }
        return result
    }

    /**
     * Returns this array unaltered.
     */
    operator fun unaryPlus(): DoubleArray2D = this

    /**
     * Returns a copy of this array with element wise negation.
     */
    operator fun unaryMinus(): DoubleArray2D =
            elementWise { -it }

    /**
     * Returns an array with the element wise addition with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the element wise addition with [other].
     */
    operator fun plus(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t + o }

    /**
     * Returns an array with the element wise subtraction with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun minus(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t - o }

    /**
     * Returns an array with the element wise multiplication with [other].
     *
     * For matrix multiplication see [matMul].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the subtraction wise addition with [other].
     */
    operator fun times(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t * o }

    /**
     * Returns an array with the element wise division with [other].
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the division wise addition with [other].
     */
    operator fun div(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> t / o }

    /**
     * Returns an array with the element wise reversed division with [other].
     *
     * The reversed division is [other] / this.
     *
     * @throws IllegalArgumentException  if this and [other] don't have the same [size]
     * @return an array containing the reversed division wise addition with [other].
     */
    fun rdiv(other: DoubleArray2D): DoubleArray2D =
            elementWise(this, other) { t, o -> o / t }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Double): DoubleArray2D =
            elementWise { it + other }

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Double): DoubleArray2D =
            elementWise { it - other }

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Double): DoubleArray2D =
            elementWise { it * other }

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Double): DoubleArray2D =
            elementWise { it / other }

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Double): DoubleArray2D =
            elementWise { other / it }

    /**
     * Returns an array with the element wise addition with [other].
     */
    operator fun plus(other: Int): DoubleArray2D =
            plus(other.toDouble())

    /**
     * Returns an array with the element wise subtraction with [other].
     */
    operator fun minus(other: Int): DoubleArray2D =
            minus(other.toDouble())

    /**
     * Returns an array with the element wise multiplication with [other].
     */
    operator fun times(other: Int): DoubleArray2D =
            times(other.toDouble())

    /**
     * Returns an array with the element wise division with [other].
     */
    operator fun div(other: Int): DoubleArray2D =
            div(other.toDouble())

    /**
     * Returns an array with the element wise reversed division with [other].
     */
    fun rdiv(other: Int): DoubleArray2D =
            elementWise { other / it }

    /**
     * Computes the sum of all its elements.
     */
    fun sum(): Double = sumBy { it }

    /**
     * Computes the average of all its elements.
     */
    fun DoubleArray1D.average(): Double = sum() / size

    fun determinant(): Double = DefaultLinearAlgebra.determinant(this)

    /**
     * Solves the linear system determined by this array and the given [image].
     *
     * This is equivalent to solving `this * result = image` in matrix notation with [other] as column vector.
     *
     * @param image  the image array of the transformation to invert
     * @return the result of solving the linear system
     * @throws IllegalArgumentException  if `this.shape0 != image.size`
     */
    fun solve(image: DoubleArray1D): DoubleArray1D = DefaultLinearAlgebra.solve(this, image)

    /**
     * Computes the matrix inverse of this array.
     *
     * @return the matrix inverse of this array
     * @throws IllegalArgumentException  if this array is not square
     */
    fun inverse(): DoubleArray2D = DefaultLinearAlgebra.inverse(this)

    /**
     * Computes the matrix multiplication between this and [other].
     *
     * This is equivalent to `this * other` in matrix notation with [other] as column vector.
     *
     * @param other  array to compute the matrix multiplication with
     * @return the result of the matrix multiplication
     * @throws IllegalArgumentException  if `this.shape1 != other.size`
     */
    infix fun matMul(other: DoubleArray1D): DoubleArray1D {
        require(this.shape1 == other.size) {
            "sizes dont match: this.shape0 = ${this.shape1} other.size = ${other.size}"
        }
        return doubleArray1D(this.shape0) { i0 ->
            sumDouble(this.indices1) { k -> this[i0, k] * other[k] }
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
    fun quadraticForm(other: DoubleArray1D): Double =
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
    fun bilinearForm(other0: DoubleArray1D, other1: DoubleArray1D): Double {
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
    infix fun matMul(other: DoubleArray2D): DoubleArray2D {
        require(this.shape1 == other.shape0) {
            "sizes dont match"
        }
        return doubleArray2D(this.shape0, other.shape1) { i0, i1 ->
            sumDouble(this.indices1) { k -> this[i0, k] * other[k, i1] }
        }
    }

    fun filter2D(filter: DoubleArray2D, padding: Double = 0.0): DoubleArray2D {

        val filterCenter0 = filter.shape0 / 2
        val filterCenter1 = filter.shape1 / 2

        return doubleArray2D(this.shape0, this.shape1) { i0, i1 ->
            sumDouble(filter.indices(0), filter.indices(1)) { j0, j1 ->
                val k0 = i0 - j0 - filterCenter0
                val k1 = i1 - j1 - filterCenter1
                if (k0 in 0 until this.shape0 && k1 in 0 until this.shape1) {
                    this[k0, k1] * filter[j0, j1]
                } else {
                    padding
                }
            }
        }
    }

}
