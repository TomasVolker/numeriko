package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.interfaces.iteration.inlinedForEachIndexed
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: DoubleArrayND,
        destination: MutableDoubleArrayND,
        operation: (Double) -> Double
) {
    requireSameShape(source, destination)
    source.inlinedForEachIndexed { indices, value ->
        destination.setDouble(indices, operation(value))
    }
}

inline fun elementWise(
        source1: DoubleArrayND,
        source2: DoubleArrayND,
        destination: MutableDoubleArrayND,
        operation: (Double, Double) -> Double
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.fastForEachIndices { indices ->
        destination.setDouble(indices, operation(source1.getDouble(indices), source2.getDouble(indices)))
    }
}

inline fun DoubleArrayND.elementWise(operation: (Double)->Double): DoubleArrayND {
    val result = doubleZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun elementWise(
        array1: DoubleArrayND,
        array2: DoubleArrayND,
        operation: (Double, Double) -> Double
): DoubleArrayND {
    requireSameShape(array1, array2)
    val result = doubleZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableDoubleArrayND.applyElementWise(
        operation: (Double) -> Double
): MutableDoubleArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableDoubleArrayND.applyElementWise(
        other: DoubleArrayND,
        operation: (Double, Double) -> Double
): MutableDoubleArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

