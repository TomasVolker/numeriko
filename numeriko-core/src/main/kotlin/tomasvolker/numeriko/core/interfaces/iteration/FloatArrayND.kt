package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.MutableFloatArrayND
import tomasvolker.numeriko.core.interfaces.factory.floatZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: FloatArrayND,
        destination: MutableFloatArrayND,
        operation: (Float) -> Float
) {
    requireSameShape(source, destination)
    source.unsafeForEachIndexed { index, value ->
        destination.setFloat(index, operation(value))
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

inline fun elementWise(
        source1: FloatArrayND,
        source2: FloatArrayND,
        destination: MutableFloatArrayND,
        operation: (Float, Float) -> Float
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.unsafeForEachIndex { index ->
        destination.setFloat(index, operation(source1.getFloat(index), source2.getFloat(index)))
    }
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

