package tomasvolker.numeriko.core.interfaces.arraynd.generic

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.arrayNDOfNulls
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

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

inline fun <T, R> elementWise(
        source: ArrayND<T>,
        destination: MutableArrayND<R>,
        operation: (T) -> R
) {
    requireSameShape(source, destination)
    source.inlinedForEachIndexed { indices, value ->
        destination.setValue(indices, operation(value))
    }
}

inline fun <T, R> ArrayND<T>.elementWise(operation: (T)->R): ArrayND<R> {
    val result = arrayNDOfNulls<R>(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result as ArrayND<R>
}

inline fun <T> MutableArrayND<T>.applyElementWise(
        operation: (T) -> T
): MutableArrayND<T> {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

