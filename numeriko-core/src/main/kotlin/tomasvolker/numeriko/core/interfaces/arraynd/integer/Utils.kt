package tomasvolker.numeriko.core.interfaces.arraynd.integer

import tomasvolker.numeriko.core.interfaces.factory.intZeros
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: IntArrayND,
        destination: MutableIntArrayND,
        operation: (Int) -> Int
) {
    requireSameShape(source, destination)
    source.inlinedForEachIndexed { indices, value ->
        destination.setInt(indices, operation(value))
    }
}

inline fun elementWise(
        source1: IntArrayND,
        source2: IntArrayND,
        destination: MutableIntArrayND,
        operation: (Int, Int) -> Int
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.fastForEachIndices { indices ->
        destination.setInt(indices, operation(source1.getInt(indices), source2.getInt(indices)))
    }
}

inline fun IntArrayND.elementWise(operation: (Int)->Int): IntArrayND {
    val result = intZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun elementWise(
        array1: IntArrayND,
        array2: IntArrayND,
        operation: (Int, Int) -> Int
): IntArrayND {
    requireSameShape(array1, array2)
    val result = intZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableIntArrayND.applyElementWise(
        operation: (Int) -> Int
): MutableIntArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableIntArrayND.applyElementWise(
        other: IntArrayND,
        operation: (Int, Int) -> Int
): MutableIntArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

