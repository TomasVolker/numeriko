package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.interfaces.factory.array1DOfNulls
import tomasvolker.numeriko.core.performance.forEach
import tomasvolker.numeriko.core.preconditions.requireSameSize

inline fun <T> Array1D<T>.forEachIndex(operation: (i0: Int) -> Unit) {

    forEach(shape0) { i0 ->
        operation(i0)
    }

}

inline fun <T, R> elementWise(source: Array1D<T>, destination: MutableArray1D<R>, operation: (T) -> R) {
    requireSameSize(source, destination)
    forEach(source.shape0) { i0 ->
        destination.setValue(i0, operation(source.getValue(i0)))
    }
}

inline fun <T1, T2, R> elementWise(source1: Array1D<T1>, source2: Array1D<T2>, destination: MutableArray1D<R>, operation: (T1, T2) -> R) {
    requireSameSize(source1, source2)
    requireSameSize(source1, destination)
    forEach(source1.shape0) { i0 ->
        destination.setValue(i0, operation(source1.getValue(i0), source2.getValue(i0)))
    }
}

inline fun <T, R> Array1D<T>.elementWise(operation: (T) -> R): Array1D<R> {
    val result = array1DOfNulls<R>(size).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result as Array1D<R>
}

inline fun <T> MutableArray1D<T>.applyMap(operation: (T) -> T): Array1D<T> {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun <T1, T2, R> elementWise(
        array1: Array1D<T1>,
        array2: Array1D<T2>,
        operation: (T1, T2) -> R
): Array1D<R> {
    requireSameSize(array1, array2)

    val result = array1DOfNulls<R>(array1.size).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result as Array1D<R>

}

inline fun <T> MutableArray1D<T>.applyElementWise(
        other: Array1D<T>,
        operation: (T, T) -> T
): Array1D<T> {
    requireSameSize(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this

}
