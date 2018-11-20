package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.*
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.reductions.product

/**
 * The parent interface of all N-dimensional arrays.
 *
 * All N-d arrays comply to this interface directly or indirectly. It exposes a read-only interface
 * for a generic array of `T` of any rank. It is encouraged to use more specific interfaces such as
 * [Array1D] or [DoubleArray2D] as they provide more functionality and type safety.
 *
 * This interface inherits from [Collection] and has out variance. To modify the array, the method [asMutable] should
 * provide a [MutableArrayND] view of it. Take in account that [asMutable] violates the out variance.
 *
 * @see MutableArrayND
 */
interface ArrayND<out T>: Collection<T> {

    /**
     * The rank of the array.
     *
     * For example, for [Array1D] and [Array2D] this equals to 1 and 2 respectively
     */
    val rank: Int get() = shape.size

    /**
     * The shape of the array.
     *
     * The shape is a IntArray1D containing the sizes on each axis.
     *
     * For example:
     *
     * `D[1, 2, 3].shape == I[3]`
     *
     * `D[D[1, 2, 3], D[4, 5, 6]].shape == I[2, 3]`
     *
     */
    val shape: IntArray1D

    /**
     * Gets the shape on the given [axis].
     *
     * This will yield the same result as `shape[axis]` but it may avoid creating the `shape` array
     * for low rank arrays.
     *
     * @throws IndexOutOfBoundsException  if [axis] is not within the range of the rank (`0 until rank`)
     */
    fun shape(axis: Int): Int = shape[axis]

    /**
     * The size of the array, meaning the amount of values it contains.
     *
     * This is equivalent to the product of the elements of the [shape].
     */
    override val size: Int get() = shape.product()


    override fun contains(element: @UnsafeVariance T): Boolean =
            any { it == element }


    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }


    override fun isEmpty(): Boolean = size == 0


    /**
     * Returns the only value if the array is of rank 0.
     *
     * @throws IllegalArgumentException  if the [rank] is not 0
     */
    fun getValue(): T = getValue(*intArrayOf())

    /**
     * Returns the element in the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     *
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @return the element at the given [indices]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun getValue(vararg indices: Int): T

    /**
     * Returns the element in the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     *
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @return the element at the given [indices]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun getValue(indices: IntArray1D): T = getValue(*indices.toIntArray())

    /**
     * Returns the element in the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     * This indices are instances of the class [Index] which can represent relative positions such
     * as [First], [Last] or [Size].
     *
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @return the element at the given [indices]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun getValue(vararg indices: Index): T = getValue(*indices.computeIndices())

    fun as0D(): Array0D<T> = DefaultArrayND0DView(this.asMutable())
    fun as1D(): Array1D<T> = DefaultArrayND1DView(this.asMutable())
    fun as2D(): Array2D<T> = DefaultArrayND2DView(this.asMutable())

    /**
     * Returns a view of the array on the given index progressions.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     * This indices are instances of the class [IntProgression] which can represent ranges with a certain step.
     * The returned [ArrayND] has the same rank as this one.
     *
     * @param indices  the index progressions of the view to retrieve, must be of the same size as [rank]
     * @return an [ArrayND] instance representing a view to the given index progressions
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if some of the progressions are out of bounds
     */
    fun getView(vararg indices: IntProgression): ArrayND<T> =
            defaultArrayNDView(this.asMutable(), indices)

    /**
     * Returns a view of the array on the given index progressions.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     * This indices are instances of the class [IndexProgression] which can represent relative ranges with a certain
     * step, such as `Size/2..Last` or `All`.
     * The returned [ArrayND] has the same rank as this one.
     *
     * @param indices  the index progressions of the view to retrieve, must be of the same size as [rank]
     * @return an [ArrayND] instance representing a view to the given index progressions
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if some of the progressions are out of bounds
     */
    fun getView(vararg indices: IndexProgression): ArrayND<T> = getView(*indices.computeIndices())

    /**
     * Returns a view of this array without the given [axis].
     *
     * The [axis] parameter must correspond to an axis with size <= 1 (`shape(axis) <= 1)`.
     */
    fun lowerRank(axis: Int): ArrayND<T> =
            DefaultArrayNDLowerRankView(this.asMutable(), axis)

    fun arrayAlongAxis(axis: Int, index: Int): ArrayND<T> =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

    fun Array<out Index>.computeIndices(): IntArray =
            IntArray(size) { i -> this[i].computeValue(shape(i)) }

    fun Array<out IndexProgression>.computeIndices(): Array<IntProgression> =
            Array(size) { i -> this[i].computeProgression(shape(i)) }

    fun Int.compute(axis: Int): Int = this
    fun IntProgression.compute(axis: Int): IntProgression = this
    fun Index.compute(axis: Int): Int = this.computeValue(shape(axis))
    fun IndexProgression.compute(axis: Int): IntProgression = this.computeProgression(shape(axis))

    /**
     * Returns a copy of this [ArrayND].
     */
    fun copy(): ArrayND<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArrayNDIterator(this)

    /**
     * Provides a mutable view of this array.
     *
     * **Caution:** this method violates the out variance.
     *
     * The default implementation for this method is just casting to [MutableArrayND]
     */
    fun asMutable(): MutableArrayND<@UnsafeVariance T> = this as MutableArrayND<T>

    fun requireValidIndices(indices: IntArray) {

        if (NumerikoConfig.checkRanges) {

            if (indices.size != rank)
                throw IllegalArgumentException("Indices [${indices.joinToString()}] are invalid for shape $shape")

            for (axis in 0 until rank) {
                // Do not use `indices(axis)` as inlining is not working
                if (indices[axis] !in 0 until shape(axis))
                    throw IndexOutOfBoundsException("Indices [${indices.joinToString()}] are out of range for shape $shape")
            }

        }

    }

    fun requireValidIndices(indices: Array<out IntProgression>) {

        if (NumerikoConfig.checkRanges) {

            if (indices.size != rank)
                throw IllegalArgumentException("Indices [${indices.joinToString()}] are invalid for shape $shape")

            for (axis in 0 until rank) {
                val indexProgression = indices[axis]
                // Do not use `indices(axis)` as inlining is not working
                if (indexProgression.first !in 0 until shape(axis))
                    throw IndexOutOfBoundsException("Indices [${indexProgression.joinToString()}] are out of range for shape $shape")
                if (indexProgression.last !in 0 until shape(axis))
                    throw IndexOutOfBoundsException("Indices [${indexProgression.joinToString()}] are out of range for shape $shape")
            }

        }

    }

    fun requireValidAxis(axis: Int) {

        if (NumerikoConfig.checkRanges) {
            // Do not use `axes` as inlining is not working
            if (axis !in 0 until rank) throw IndexOutOfBoundsException("Axis index $axis invalid for rank $rank")

        }

    }

    fun requireValidIndex(i: Int, axis: Int) {

        if (NumerikoConfig.checkRanges) {
            // Do not use `indices()` as inlining is not working
            if (i !in 0 until shape(axis)) throw IndexOutOfBoundsException("Index $i on axis 0 is out of size ${shape(axis)}")
        }

    }

    fun requireValidIndexRange(i: IntProgression, axis: Int) {

        if (NumerikoConfig.checkRanges) {
            // Do not use `indices` as inlining is not working
            if (i.first !in 0 until shape(axis))
                throw IndexOutOfBoundsException("Index ${i.first} in axis $axis is out of shape $shape")
            if (i.last  !in 0 until shape(axis))
                throw IndexOutOfBoundsException("Index ${i.last} in axis $axis is out of shape $shape")
        }

    }

}