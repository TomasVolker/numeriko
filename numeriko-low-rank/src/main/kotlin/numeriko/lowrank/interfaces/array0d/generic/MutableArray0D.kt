package numeriko.lowrank.interfaces.array0d.generic

import tomasvolker.core.annotations.*
import numeriko.lowrank.interfaces.array1d.generic.MutableArray1D
import tomasvolker.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.core.preconditions.requireValidIndices
import tomasvolker.core.preconditions.rankError
import tomasvolker.core.preconditions.rankError0DMessage

interface MutableArray0D<T>: Array0D<T>, MutableArrayND<T> {

    override fun setValue(indices: IntArray, value: T) {
        requireValidIndices(indices)
        return setValue(value)
    }

    override fun setValue(value: T)

    override fun as0D() = this

    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
    override fun as1D(): Nothing = rankError(1)
    @tomasvolker.numeriko.core.annotations.CompileTimeError(message = rankError0DMessage, level = tomasvolker.numeriko.core.annotations.Level.ERROR)
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
