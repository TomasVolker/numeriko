package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.factory.mutableIntZeros

inline fun IntArray1D.elementWise(operation: (Int) -> Int): MutableIntArray1D {
    val result = mutableIntZeros(size)
    for (i in indices) {
        result[i] = operation(this[i])
    }
    return result

}

inline fun MutableIntArray1D.applyMap(operation: (Int) -> Int): MutableIntArray1D {
    for (i in indices) {
        this[i] = operation(this[i])
    }
    return this

}

inline fun elementWise(array1: IntArray1D, array2: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    requireSameSize(array1, array2)

    val result = mutableIntZeros(array1.size)
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i])
    }
    return result

}

inline fun MutableIntArray1D.applyElementWise(other: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    requireSameSize(this, other)

    for (i in this.indices) {
        this[i] = operation(this[i], other[i])
    }
    return this

}

inline fun IntArray1D.sumBy(operation: (Int) -> Int): Int {
    var result = 0
    for (i in indices) {
        result += operation(this[i])
    }
    return result
}

inline fun IntArray1D.sumByDouble(operation: (Int) -> Double): Double {
    var result = 0.0
    for (i in indices) {
        result += operation(this[i])
    }
    return result
}
