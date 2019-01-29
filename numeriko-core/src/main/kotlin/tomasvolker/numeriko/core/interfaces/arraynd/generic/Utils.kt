package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.index.Index
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.index.toIndexProgression
import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.lastIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayND0DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayND1DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.view.DefaultArrayND2DView
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.factory.toIntArray1D
import tomasvolker.numeriko.core.performance.fastForEachIndices

val ArrayND<*>.volume get(): Int = size
val ArrayND<*>.axes get(): IntRange = 0 until rank
val ArrayND<*>.lastAxis get(): Int = rank - 1

fun ArrayND<*>.indices(axis: Int): IntRange = 0 until shape(axis)

/**
 * Executes [block] with each indexArray of the ArrayND.
 *
 * This function provides a copy of each index on each iteration.
 * For faster iteration use [unsafeForEachIndices].
 */
inline fun ArrayND<*>.forEachIndices(block: (indices: IntArray1D)->Unit) =
        fastForEachIndices { indices -> block(indices.toIntArray1D()) }


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

fun <T> ArrayND<T>.unsafeGetView(vararg indices: Any): ArrayND<T> {
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

fun <T> MutableArrayND<T>.unsafeGetView(vararg indices: Any): MutableArrayND<T> =
        (this as ArrayND<T>).unsafeGetView(*indices).asMutable()
