package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.interfaces.factory.mutableArray2DOfNulls
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun <T> Array2D<T>.forEachIndex(operation: (i0: Int, i1: Int) -> Unit) {

    for (i0 in 0 until shape0) {
        for (i1 in 0 until shape1) {
            operation(i0, i1)
        }
    }

}

inline fun <T, R> elementWise(source: Array2D<T>, destination: MutableArray2D<R>, operation: (T) -> R) {
    requireSameShape(source, destination)
    source.forEachIndex { i0, i1 ->
        destination.setValue(operation(source.getValue(i0, i1)), i0, i1)
    }

}

inline fun <T1, T2, R> elementWise(source1: Array2D<T1>, source2: Array2D<T2>, destination: MutableArray2D<R>, operation: (T1, T2) -> R) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndex { i0, i1 ->
        destination.setValue(operation(source1.getValue(i0, i1), source2.getValue(i0, i1)), i0, i1)
    }
}

inline fun <T, R> Array2D<T>.elementWise(operation: (T) -> R): MutableArray2D<R> {
    val result = mutableArray2DOfNulls<R>(shape0, shape1)
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result as MutableArray2D<R>
}

inline fun <T, A: MutableArray2D<T>> A.applyMap(operation: (T) -> T): A {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun <T1, T2, R> elementWise(array1: Array2D<T1>, array2: Array2D<T2>, operation: (T1, T2) -> R): MutableArray2D<R> {
    requireSameShape(array1, array2)

    val result = mutableArray2DOfNulls<R>(array1.shape0, array1.shape1)
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result as MutableArray2D<R>

}

inline fun <T, A: MutableArray2D<T>> A.applyElementWise(other: Array2D<T>, operation: (T, T) -> T): A {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

fun <T> Array2D<T>.checkIndices(i0: Int, i1: Int) {

    if (i0 !in 0 until shape0) {
        throw IndexOutOfBoundsException(i0)
    }

    if (i1 !in 0 until shape1) {
        throw IndexOutOfBoundsException(i1)
    }

}