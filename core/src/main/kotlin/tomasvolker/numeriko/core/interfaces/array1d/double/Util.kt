package tomasvolker.numeriko.core.interfaces.array1d.double

import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros

inline fun DoubleArray1D.elementWise(operation: (Double) -> Double): MutableDoubleArray1D {
    val result = mutableDoubleZeros(size)
    for (i in indices) {
        result[i] = operation(this[i])
    }
    return result
}

inline fun MutableDoubleArray1D.applyMap(operation: (Double) -> Double): MutableDoubleArray1D {
    for (i in indices) {
        this[i] = operation(this[i])
    }
    return this
}

inline fun elementWise(array1: DoubleArray1D, array2: DoubleArray1D, operation: (Double, Double) -> Double): MutableDoubleArray1D {
    requireSameSize(array1, array2)

    val result = mutableDoubleZeros(array1.size)
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result

}

inline fun MutableDoubleArray1D.applyElementWise(other: DoubleArray1D, operation: (Double, Double) -> Double): MutableDoubleArray1D {
    requireSameSize(this, other)

    for (i in this.indices) {
        this[i] = operation(this[i], other[i])
    }
    return this

}

inline fun DoubleArray1D.sumBy(operation: (Double) -> Double): Double {
    var result = 0.0
    for (i in indices) {
        result += operation(this[i])
    }
    return result
}