package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.slicing.*
import tomasvolker.numeriko.core.preconditions.illegalArgument
import tomasvolker.numeriko.core.primitives.productInt

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
    override val size: Int get() = productInt(0 until rank) { i -> shape(i) }

    override fun contains(element: @UnsafeVariance T): Boolean =
            any { it == element }


    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean =
            elements.all { this.contains(it) }


    override fun isEmpty(): Boolean = size == 0

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
    fun getValue(indices: IntArray): T

    fun getValue(indices: IntArray1D): T = getValue(indices.toIntArray())

    fun getValue(): T = getValue(intArrayOf())
    fun getValue(i0: Int): T = getValue(intArrayOf(i0))
    fun getValue(i0: Int, i1: Int): T = getValue(intArrayOf(i0, i1))
    fun getValue(i0: Int, i1: Int, i2: Int): T = getValue(intArrayOf(i0, i1, i2))
    fun getValue(i0: Int, i1: Int, i2: Int, i3: Int): T = getValue(intArrayOf(i0, i1, i2, i3))
    fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int): T = getValue(intArrayOf(i0, i1, i2, i3, i4))
    fun getValue(i0: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): T = getValue(intArrayOf(i0, i1, i2, i3, i4, i5))

    /**
     * Low level array slicing.
     *
     * This function returns a view implementing an arbitrary permuted strided slicing.
     *
     * @param array The backing array
     * @param transpose Array of size `shape.size` containing the axes on the backing array corresponding to the axes
     * on the view. If `transpose[a] < 0` then `shape[a] == 1`.
     * @param shape The shape of the resulting view
     * @param strides Array of size `shape.size` containing the stride corresponding to each dimension.
     * @param origin Array of size `array.rank` containing the indices on `array` corresponding to all zeros in the view
     */
    fun slice(
            slice: ArraySlice
    ): ArrayND<T> = DefaultSliceArrayND(
            array = this.asMutable(),
            slice = slice
    )

    fun canReshapeTo(shape: IntArray1D): Boolean = canSliceReshapeTo(shape)

    fun reshape(shape: IntArray1D, copyIfNecessary: Boolean = true): ArrayND<T> {

        if (canReshapeTo(shape)) {
            return sliceReshape(shape)
        } else {

            val copy = if (copyIfNecessary)
                copy()
            else
                illegalArgument("Cannot reshape ${this.shape} to $shape without copying")

            if (!copy.canReshapeTo(shape))
                error("Cannot reshape a copy of the array")

            return copy.reshape(shape, copyIfNecessary = false)

        }

    }

    /**
     * Returns a copy of this [ArrayND].
     */
    fun copy(): ArrayND<T> = copy(this)

    override fun iterator(): Iterator<T> = arrayIterator()
    fun arrayIterator(): ArrayNDIterator<T> = DefaultArrayNDIterator(this)

    /**
     * Provides a mutable view of this array.
     *
     * **Caution:** this method violates the out variance.
     *
     * The default implementation for this method is just casting to [MutableArrayND]
     */
    fun asMutable(): MutableArrayND<@UnsafeVariance T> = this as MutableArrayND<T>

}

