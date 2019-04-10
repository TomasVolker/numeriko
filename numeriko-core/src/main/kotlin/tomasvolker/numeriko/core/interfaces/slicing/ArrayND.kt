package tomasvolker.numeriko.core.interfaces.slicing

import tomasvolker.numeriko.core.annotations.*
import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.lowrank.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.lowrank.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.preconditions.rankError0DMessage
import tomasvolker.numeriko.core.preconditions.rankError1DMessage
import tomasvolker.numeriko.core.preconditions.rankError2DMessage

private fun Array<out Any>.convertToIndicesProgression(): Array<IndexProgression> =
        map { index ->
            when (index) {
                is Int -> (index..index).toIndexProgression()
                is Index -> (index..index)
                is IntProgression -> index.toIndexProgression()
                is IndexProgression -> index
                else -> throw IllegalArgumentException("index is not of type Int, Index, IntProgression or IndexProgression")
            }
        }.toTypedArray()



operator fun <T> ArrayND<T>.get(vararg indices: Any): ArrayND<T> {
    var view = getView(*indices.convertToIndicesProgression())
    var reductions = 0
    for ((axis, index) in indices.withIndex()) {
        if (index is Int || index is Index) {
            view = view.lowerRank(axis-reductions)
            reductions++
        }
    }
    return view
}


operator fun <T> MutableArrayND<T>.get(vararg indices: Any): MutableArrayND<T> =
        (this as ArrayND<T>).get(*indices).asMutable()

operator fun <T> MutableArrayND<T>.set(vararg indices: Any, value: ArrayND<T>): Unit =
        this.get(*indices).setValue(value)


@CompileTimeError(message = rankError0DMessage, level = Level.ERROR)
operator fun Array0D<*>.get(vararg indices: Any): Nothing = rankError(-1)

@CompileTimeError(message = rankError1DMessage, level = Level.ERROR)
operator fun Array1D<*>.get(vararg indices: Any): Nothing = rankError(-1)

@CompileTimeError(message = rankError2DMessage, level = Level.ERROR)
operator fun Array2D<*>.get(vararg indices: Any): Nothing = rankError(-1)

