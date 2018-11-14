package tomasvolker.numeriko.core.interfaces.array2d.double

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DTransposeView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.DefaultMutableDoubleArray2DView
import tomasvolker.numeriko.core.interfaces.array2d.double.view.MutableDoubleArray2DCollapseView
import tomasvolker.numeriko.core.interfaces.array2d.generic.*
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.defaultFactory
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.linearalgebra.DefaultLinearAlgebra
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.primitives.sumDouble

interface DoubleArray2D: Array2D<Double>, DoubleArrayND {

    override fun getDouble(vararg indices: Int): Double {
        requireValidIndices(indices)
        return getDouble(indices[0], indices[1])
    }

    override fun getValue(vararg indices: Int): Double =
            getValue(*indices)

    override fun getValue(i0: Int, i1: Int): Double =
            getDouble(i0, i1)

    fun getDouble(i0: Int, i1: Int): Double

    fun getDouble(i0: Index, i1: Index): Double =
            getDouble(i0.computeValue(shape0), i1.computeValue(shape1))

    operator fun get(i0: Int, i1: Int): Double = getDouble(i0, i1)

    override fun getView(i0: IntProgression, i1: IntProgression): DoubleArray2D =
            DefaultMutableDoubleArray2DView(
                    array = this.asMutable(),
                    offset0 = i0.first,
                    offset1 = i1.first,
                    shape0 = i0.count(),
                    shape1 = i1.count(),
                    stride0 = i0.step,
                    stride1 = i1.step
            )

    override fun getView(i0: Int, i1: IntProgression): DoubleArray1D =
            MutableDoubleArray2DCollapseView(
                    DefaultMutableDoubleArray2DView(
                            array = this.asMutable(),
                            offset0 = i0,
                            offset1 = i1.first,
                            shape0 = 1,
                            shape1 = i1.count(),
                            stride0 = 1,
                            stride1 = i1.step
                    )
            )

    override fun getView(i0: IntProgression, i1: Int): DoubleArray1D =
            MutableDoubleArray2DCollapseView(
                    DefaultMutableDoubleArray2DView(
                            array = this.asMutable(),
                            offset0 = i0.first,
                            offset1 = i1,
                            shape0 = i0.count(),
                            shape1 = 1,
                            stride0 = i0.step,
                            stride1 = 1
                    )
            )

    override fun getView(i0: IndexProgression, i1: IndexProgression): DoubleArray2D =
            getView(i0.computeProgression(shape0), i1.computeProgression(shape1))

    override fun getView(i0: Int, i1: IndexProgression): DoubleArray1D =
            getView(i0, i1.computeProgression(shape1))

    override fun getView(i0: IndexProgression, i1: Int): DoubleArray1D =
            getView(i0.computeProgression(shape0), i1)

    fun transpose(): DoubleArray2D =
            DefaultMutableDoubleArray2DTransposeView(this.asMutable())

    override fun copy(): DoubleArray2D = defaultFactory.copy(this)

    override fun iterator(): DoubleIterator =
            DefaultDoubleArray2DIterator(this)


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
        require(this.shape0 == other.size) {
            "sizes dont match"
        }
        return doubleArray1D(this.shape1) { i0 ->
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
            sumDouble(this.indices0) { k -> this[i0, k] * other[k, i1] }
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


    override fun asMutable(): MutableDoubleArray2D = this as MutableDoubleArray2D

}
