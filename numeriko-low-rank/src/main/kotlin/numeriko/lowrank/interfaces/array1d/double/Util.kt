package numeriko.lowrank.interfaces.array1d.double

import numeriko.lowrank.interfaces.array1d.generic.indices
import tomasvolker.core.interfaces.factory.doubleZeros
import tomasvolker.core.preconditions.requireSameSize

inline fun elementWise(source: DoubleArray1D, destination: MutableDoubleArray1D, operation: (Double) -> Double) {
    requireSameSize(source, destination)
    for (i in source.indices) {
        destination[i] = operation(source[i])
    }
}

inline fun elementWise(
        source1: DoubleArray1D,
        source2: DoubleArray1D,
        destination: MutableDoubleArray1D,
        operation: (Double, Double) -> Double
) {
    requireSameSize(source1, source2)
    requireSameSize(source1, destination)
    for (i in source1.indices) {
        destination[i] = operation(source1[i], source2[i])
    }
}

inline fun DoubleArray1D.elementWise(operation: (Double) -> Double): DoubleArray1D {
    val result = doubleZeros(size).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableDoubleArray1D.applyElementWise(operation: (Double) -> Double): MutableDoubleArray1D {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(array1: DoubleArray1D, array2: DoubleArray1D, operation: (Double, Double) -> Double): DoubleArray1D {
    requireSameSize(array1, array2)

    val result = doubleZeros(array1.size).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableDoubleArray1D.applyElementWise(other: DoubleArray1D, operation: (Double, Double) -> Double): MutableDoubleArray1D {
    requireSameSize(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this

}

inline fun DoubleArray1D.sumBy(operation: (Double) -> Double): Double {
    var result = 0.0
    for (i in indices) {
        result += operation(this[i])
    }
    return result
}