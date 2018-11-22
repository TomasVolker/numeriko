package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.double.MutableDoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.generic.MutableArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND0DView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND1DView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND2DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.*
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
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

    override fun as0D(): MutableArray0D<T> = DefaultArrayND0DView(this)
    override fun as1D(): MutableArray1D<T> = DefaultArrayND1DView(this)
    override fun as2D(): MutableArray2D<T> = DefaultArrayND2DView(this)

    override fun getView(vararg indices: IntProgression): MutableArrayND<T> =
            defaultArrayNDView(this, indices)

    override fun getView(vararg indices: IndexProgression): MutableArrayND<T> =
            getView(*indices.computeIndices())

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
    fun setValue(value: T, vararg indices: Int): Unit

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
    fun setValue(value: T, indices: IntArray1D): Unit =
            setValue(value, *indices.toIntArray())

    /**
     * Sets [value] to the given indices.
     *
     * The size of [indices] has to be the same as [rank], if not an exception is thrown.
     * This indices are instances of the class [Index] which can represent relative positions such
     * as [First], [Last] or [Size].
     *
     * @param value  the value to set
     * @param indices  the indices of the element to retrieve, must be of the same size as [rank]
     * @throws IllegalArgumentException  if the size of [indices] does not match [rank]
     * @throws IndexOutOfBoundsException  if the indices are out of bounds
     */
    fun setValue(value: T, vararg indices: Index): Unit =
            setValue(value, *indices.computeIndices())

    fun setValue(value: T): Unit = setValue(value, *intArrayOf())

    fun setValue(value: ArrayND<T>) {
        requireSameShape(this, value)
        // Anti alias copy
        val copy = value.copy()
        unsafeForEachIndices { indices ->
            setValue(copy.getValue(indices), indices)
        }
    }


    fun setView(value: ArrayND<T>, vararg indices: IntProgression): Unit =
            getView(*indices).asMutable().setValue(value)

    fun setView(value: ArrayND<T>, vararg indices: IndexProgression): Unit =
            setView(value, *Array(indices.size) { i -> indices[i].computeProgression(shape[i]) })

    override fun lowerRank(axis: Int): MutableArrayND<T> =
            DefaultArrayNDLowerRankView(this, axis)

    override fun higherRank(axis: Int): MutableArrayND<T> =
            DefaultArrayNDHigherRankView(this, axis)

    override fun arrayAlongAxis(axis: Int, index: Int): MutableArrayND<T> =
            getView(*Array(rank) { ax -> if (ax == axis) IntRange(index, index).toIndexProgression() else All })

}