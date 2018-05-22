package tomasvolker.numeriko.core.interfaces.integer.array1d

import tomasvolker.numeriko.core.interfaces.factory.mutableIntZeros

inline fun IntArray1D.elementWise(operation: (Int) -> Int): MutableIntArray1D {
    val result = mutableIntZeros(size)
    for (i in indices) {
        result[i] = operation(this[i])
    }
    return result

}

inline fun MutableIntArray1D.applyElementWise(operation: (Int) -> Int): MutableIntArray1D {
    for (i in indices) {
        this[i] = operation(this[i])
    }
    return this

}

inline fun elementWise(array1: IntArray1D, array2: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    require(array1.size == array2.size) {
        "Arrays are not of the same size"
    }

    val result = mutableIntZeros(array1.size)
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result

}

inline fun MutableIntArray1D.applyElementWise(other: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    require(this.size == other.size) {
        "Arrays are not of the same size"
    }

    for (i in this.indices) {
        this[i] = operation(this[i], other[i])
    }
    return this

}