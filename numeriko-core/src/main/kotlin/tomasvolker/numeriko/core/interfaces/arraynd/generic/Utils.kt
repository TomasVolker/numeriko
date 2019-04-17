package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices

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
inline fun ArrayND<*>.forEachIndices(block: (indices: IntArrayND)->Unit) =
        fastForEachIndices { indices -> block(indices.toIntArrayND()) }

