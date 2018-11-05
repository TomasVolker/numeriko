package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.factory.mutableArray1DOfNulls

fun <T> Array1D<T>.asMutable(): MutableArray1D<T> = this as MutableArray1D<T>

inline fun <T, R> elementWise(source: Array1D<T>, destination: MutableArray1D<R>, operation: (T) -> R) {
    requireSameSize(source, destination)
    for (i in source.indices) {
        destination.setValue(operation(source.getValue(i)), i)
    }
}

inline fun <T1, T2, R> elementWise(source1: Array1D<T1>, source2: Array1D<T2>, destination: MutableArray1D<R>, operation: (T1, T2) -> R) {
    requireSameSize(source1, source2)
    requireSameSize(source1, destination)
    for (i in source1.indices) {
        destination.setValue(operation(source1.getValue(i), source2.getValue(i)), i)
    }
}

inline fun <T, R> Array1D<T>.elementWise(operation: (T) -> R): MutableArray1D<R> {
    val result = mutableArray1DOfNulls<R>(size)
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result as MutableArray1D<R>
}

inline fun <T> MutableArray1D<T>.applyMap(operation: (T) -> T): MutableArray1D<T> {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun <T1, T2, R> elementWise(array1: Array1D<T1>, array2: Array1D<T2>, operation: (T1, T2) -> R): MutableArray1D<R> {
    requireSameSize(array1, array2)

    val result = mutableArray1DOfNulls<R>(array1.size)
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result as MutableArray1D<R>

}

inline fun <T> MutableArray1D<T>.applyElementWise(other: Array1D<T>, operation: (T, T) -> T): MutableArray1D<T> {
    requireSameSize(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this

}
