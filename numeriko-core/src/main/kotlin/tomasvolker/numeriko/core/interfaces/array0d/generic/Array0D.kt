package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.annotations.CompileTimeError
import tomasvolker.numeriko.core.annotations.Level
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.preconditions.requireValidAxis
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.interfaces.factory.copy
import tomasvolker.numeriko.core.interfaces.factory.intArray1DOf
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError0DMessage

interface Array0D<out T>: ArrayND<T> {

    override val rank: Int get() = 0

    override val shape: IntArray1D
        get() = intArray1DOf()

    override fun shape(axis: Int): Int {
        requireValidAxis(axis)
        throw IllegalStateException()
    }

    override val size: Int get() = 1

    override fun as0D() = this

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

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