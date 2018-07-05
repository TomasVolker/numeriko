package tomasvolker.numeriko.core.interfaces.array1d.integer

import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.factory.mutableIntZeros

inline fun elementWise(source: IntArray1D, destination: MutableIntArray1D, operation: (Int) -> Int) {
    requireSameSize(source, destination)
    for (i in source.indices) {
        destination[i] = operation(source[i])
    }
}

inline fun elementWise(source1: IntArray1D, source2: IntArray1D, destination: MutableIntArray1D, operation: (Int, Int) -> Int) {
    requireSameSize(source1, source2)
    requireSameSize(source1, destination)
    for (i in source1.indices) {
        destination[i] = operation(source1[i], source2[i])
    }
}

inline fun IntArray1D.elementWise(operation: (Int) -> Int): MutableIntArray1D {
    val result = mutableIntZeros(size)
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableIntArray1D.applyMap(operation: (Int) -> Int): MutableIntArray1D {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this

}

inline fun elementWise(array1: IntArray1D, array2: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    requireSameSize(array1, array2)

    val result = mutableIntZeros(array1.size)
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableIntArray1D.applyElementWise(other: IntArray1D, operation: (Int, Int) -> Int): MutableIntArray1D {
    requireSameSize(this, other)

    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
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
