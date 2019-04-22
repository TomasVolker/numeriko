package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: IntArray1D,
        destination: MutableIntArray1D,
        operation: (Int)->Int
) {
    requireSameShape(source, destination)
    source.forEachIndex1 { i ->
        destination[i] = operation(source[i])
    }
}

inline fun IntArray1D.elementWise(operation: (Int)->Int): IntArray1D {
    val result = intZeros(size).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableIntArray1D.applyElementWise(
        operation: (Int)->Int
): MutableIntArray1D {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableIntArray1D.applyElementWise(
        other: IntArray1D,
        operation: (Int, Int)->Int
): MutableIntArray1D {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(
        source1: IntArray1D,
        source2: IntArray1D,
        destination: MutableIntArray1D,
        operation: (Int, Int)->Int
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndex1 { i ->
        destination[i] = operation(source1[i], source2[i])
    }
}

inline fun elementWise(
        array1: IntArray1D,
        array2: IntArray1D,
        operation: (Int, Int)->Int
): IntArray1D {
    requireSameShape(array1, array2)
    val result = intZeros(array1.size).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}


