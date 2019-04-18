package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.interfaces.slicing.PermutedSlice
import tomasvolker.numeriko.core.preconditions.requireSameShape

/**
 * The parent interface of all mutable N-dimensional arrays.
 *
 * All mutable N-d arrays comply to this interface directly or indirectly. It exposes a mutable interface
 * for a generic array of `T` of any rank. It is encouraged to use more specific interfaces such as
 * [MutableArray1D] or [MutableDoubleArray2D] as they provide more functionality and type safety.
 *
 * This interface inherits from [ArrayND], so it can be exposed as read-only by upcasting.
 *
 * @see ArrayND
 */
interface MutableArrayND<T>: ArrayND<T> {

    /**
     * Sets [value] to the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     *
     * @param value  the value to set
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun setValue(indices: IntArray, value: T): Unit

    /**
     * Sets [value] to the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     *
     * @param value  the value to set
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun setValue(indices: IntArrayND, value: T): Unit =
            setValue(indices.toIntArray(), value)

    fun setValue(value: ArrayND<T>) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        copy.unsafeForEachIndex { indices ->
            setValue(indices, copy.getValue(indices))
        }
    }

    override fun getPermutedSlice(
            slice: PermutedSlice
    ): MutableArrayND<T> = DefaultPermutedSliceArrayND(
            array = this,
            permutedSlice = slice
    )

}