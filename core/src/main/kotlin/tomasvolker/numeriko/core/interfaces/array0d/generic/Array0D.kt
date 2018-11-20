package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf

interface Array0D<out T>: ArrayND<T> {

    override val rank: Int get() = 0

    override val shape: IntArray1D
        get() = intArray1DOf()

    override fun shape(axis: Int): Int {
        requireValidAxis(axis)
        throw IllegalStateException()
    }

    override val size: Int get() = 1

    override fun getValue(vararg indices: Int): T {
        requireValidIndices(indices)
        return getValue(indices[0])
    }

    override fun getValue(): T

    fun getView(): Array0D<T> = this

    override fun copy(): Array0D<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArray0DIterator(this)

    override fun asMutable(): MutableArray0D<@UnsafeVariance T> = this as MutableArray0D<T>

}