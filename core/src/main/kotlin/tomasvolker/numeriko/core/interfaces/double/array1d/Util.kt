package tomasvolker.numeriko.core.interfaces.double.array1d

import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros

inline fun DoubleArray1D.elementWise(operation: (Double) -> Double): DoubleArray1D {
    val result = mutableDoubleZeros(size)
    for (i in indices) {
        result[i] = operation(this[i])
    }
    return result

}

inline fun elementWise(array1: DoubleArray1D, array2: DoubleArray1D, operation: (Double, Double) -> Double): DoubleArray1D {
    require(array1.size == array2.size) {
        "Arrays are not of the same size"
    }

    val result = mutableDoubleZeros(array1.size)
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result

}