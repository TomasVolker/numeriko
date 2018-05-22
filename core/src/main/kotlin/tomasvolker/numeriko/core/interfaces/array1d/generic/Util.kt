package tomasvolker.numeriko.core.interfaces.array1d.generic

import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.factory.mutableArray1DOfNulls

inline fun <T, R> Array1D<T>.elementWise(operation: (T) -> R): MutableArray1D<R> {
    val result = mutableArray1DOfNulls<R>(size)
    for (i in indices) {
        result.setValue(operation(this.getValue(i)), i)
    }
    return result as MutableArray1D<R>
}

inline fun <T> MutableArray1D<T>.applyMap(operation: (T) -> T): MutableArray1D<T> {
    for (i in indices) {
        this.setValue(operation(this.getValue(i)), i)
    }
    return this
}

inline fun <T1, T2, R> elementWise(array1: Array1D<T1>, array2: Array1D<T2>, operation: (T1, T2) -> R): MutableArray1D<R> {
    requireSameSize(array1, array2)

    val result = mutableArray1DOfNulls<R>(array1.size)
    for (i in array1.indices) {
        result.setValue(operation(array1.getValue(i), array2.getValue(i)), i)
    }
    return result as MutableArray1D<R>

}

inline fun <T> MutableArray1D<T>.applyElementWise(other: Array1D<T>, operation: (T, T) -> T): MutableArray1D<T> {
    requireSameSize(this, other)

    for (i in this.indices) {
        this.setValue(operation(this.getValue(i), other.getValue(i)), i)
    }
    return this

}
