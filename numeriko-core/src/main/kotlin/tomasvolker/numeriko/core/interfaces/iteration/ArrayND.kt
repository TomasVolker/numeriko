package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.arrayNDOfNulls
import tomasvolker.numeriko.core.interfaces.factory.toIntArrayND
import tomasvolker.numeriko.core.performance.forEach
import tomasvolker.numeriko.core.preconditions.requireRank
import tomasvolker.numeriko.core.preconditions.requireSameShape

/**
 * Executes [block] with each indexArray of the ArrayND.
 *
 * This function provides a copy of each index on each iteration.
 * For faster iteration use [unsafeForEachIndex].
 */
inline fun ArrayND<*>.forEachIndex(block: (indices: IntArrayND)->Unit) =
        unsafeForEachIndex { indices -> block(indices.toIntArrayND()) }

inline fun <T> ArrayND<T>.unsafeForEachIndexed(function: (index: IntArray, value: T)->Unit) {
    unsafeForEachIndex { index ->
        function(index, getValue(index))
    }
}

inline fun <T, R> elementWise(
        source: ArrayND<T>,
        destination: MutableArrayND<R>,
        operation: (T) -> R
) {
    requireSameShape(source, destination)
    source.unsafeForEachIndexed { indices, value ->
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


inline fun <T> ArrayND<T>.forEachIndex1(block: (i0: Int)->Unit) {
    requireRank(1)
    forEach(shape(0), block)
}

inline fun <T> ArrayND<T>.forEachIndex2(block: (i0: Int, i1: Int)->Unit) {
    requireRank(2)
    forEach(shape(0), shape(1), block)
}

inline fun <T> ArrayND<T>.forEachIndex3(block: (i0: Int, i1: Int, i2: Int)->Unit) {
    requireRank(3)
    forEach(shape(0), shape(1), shape(2), block)
}

inline fun <T> ArrayND<T>.inlinedForEach(block: (value: T)->Unit) {
    unsafeForEachIndex { index ->
        block(getValue(index))
    }
}

inline fun <T> ArrayND<T>.inlinedForEach1(block: (value: T)->Unit) {
    forEachIndex1 { i0 ->
        block(getValue(i0))
    }
}

inline fun <T> ArrayND<T>.inlinedForEach2(block: (value: T)->Unit) {
    forEachIndex2 { i0, i1 ->
        block(getValue(i0, i1))
    }
}

inline fun <T> ArrayND<T>.inlinedForEach3(block: (value: T)->Unit) {
    forEachIndex3 { i0, i1, i2 ->
        block(getValue(i0, i1, i2))
    }
}

inline fun <T> ArrayND<T>.forEachIndexed1(function: (i0: Int, value: T)->Unit) {
    forEachIndex1 { i0 ->
        function(i0, getValue(i0))
    }
}

inline fun <T> ArrayND<T>.forEachIndexed2(function: (i0: Int, i1: Int, value: T)->Unit) {
    forEachIndex2 { i0, i1 ->
        function(i0, i1, getValue(i0, i1))
    }
}

