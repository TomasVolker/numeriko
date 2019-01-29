package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.interfaces.array0d.double.DefaultDoubleArray0DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.double.view.DefaultDoubleArray1DHigherRankView
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
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

    override fun getValue(indices: IntArray): T {
        requireValidIndices(indices)
        return getValue()
    }

    override fun getValue(): T

    fun getView(): Array0D<T> = this

    override fun lowerRank(axis: Int): Nothing {
        requireValidAxis(axis)
        error("requireValidAxis should fail")
    }

    fun higherRank(): Array1D<T> = higherRank(0)

    override fun higherRank(axis: Int): Array1D<T> {
        require(axis == 0)
        return DefaultArray0DHigherRankView(this.asMutable())
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        requireValidAxis(axis)
        error("requireValidAxis should fail")
    }

    override fun copy(): Array0D<T> = copy(this)

    override fun iterator(): Iterator<T> = DefaultArray0DIterator(this)

    override fun asMutable(): MutableArray0D<@UnsafeVariance T> = this as MutableArray0D<T>

}