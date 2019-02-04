package tomasvolker.numeriko.core.interfaces.array0d.generic

import tomasvolker.numeriko.core.annotations.*
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.requireValidIndices
import tomasvolker.numeriko.core.preconditions.rankError
import tomasvolker.numeriko.core.preconditions.rankError0DMessage

interface MutableArray0D<T>: Array0D<T>, MutableArrayND<T> {

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        return setValue(value)
    }

    override fun setValue(value: T)

    override fun as0D() = this

    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
    override fun as2D(): Nothing = rankError(2)

    fun setValue(other: Array0D<T>) {
        setValue(other.getValue())
    }

    override fun getView(): MutableArray0D<T> = this

    override fun lowerRank(axis: Int): Nothing {
        super<Array0D>.lowerRank(axis)
    }

    override fun arrayAlongAxis(axis: Int, index: Int): Nothing {
        super<Array0D>.arrayAlongAxis(axis, index)
    }

    override fun higherRank(): MutableArray1D<T> = higherRank(0)

    override fun higherRank(axis: Int): MutableArray1D<T> {
        require(axis == 0)
        return DefaultArray0DHigherRankView(this)
    }

}
