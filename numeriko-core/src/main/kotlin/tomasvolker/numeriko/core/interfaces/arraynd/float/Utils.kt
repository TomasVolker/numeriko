package tomasvolker.numeriko.core.interfaces.arraynd.float

import tomasvolker.numeriko.core.interfaces.factory.floatZeros
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: FloatArrayND,
        destination: MutableFloatArrayND,
        operation: (Float) -> Float
) {
    requireSameShape(source, destination)
    source.inlinedForEachIndexed { indices, value ->
        destination.setFloat(indices, operation(value))
    }
}

inline fun elementWise(
        source1: FloatArrayND,
        source2: FloatArrayND,
        destination: MutableFloatArrayND,
        operation: (Float, Float) -> Float
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.fastForEachIndices { indices ->
        destination.setFloat(indices, operation(source1.getFloat(indices), source2.getFloat(indices)))
    }
}

inline fun FloatArrayND.elementWise(operation: (Float)->Float): FloatArrayND {
    val result = floatZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun elementWise(
        array1: FloatArrayND,
        array2: FloatArrayND,
        operation: (Float, Float) -> Float
): FloatArrayND {
    requireSameShape(array1, array2)
    val result = floatZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableFloatArrayND.applyElementWise(
        operation: (Float) -> Float
): MutableFloatArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableFloatArrayND.applyElementWise(
        other: FloatArrayND,
        operation: (Float, Float) -> Float
): MutableFloatArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

