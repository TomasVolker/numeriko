package tomasvolker.numeriko.complex.interfaces.array1d

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.interfaces.factory.complexZeros
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.preconditions.requireSameSize

inline fun ComplexArray1D.elementWise(
        operation: (Complex)->Number
): ComplexArray1D {
    val result = complexZeros(size).asMutable()
    for (i in indices) {
        result[i] = operation(this[i]).toComplex()
    }
    return result
}

inline fun elementWise(
        array1: ComplexArray1D,
        array2: ComplexArray1D,
        operation: (Complex, Complex)->Number
): ComplexArray1D {
    requireSameSize(array1, array2)
    val result = complexZeros(array1.size).asMutable()
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i]).toComplex()
    }
    return result
}

inline fun elementWise(
        array1: ComplexArray1D,
        array2: DoubleArray1D,
        operation: (Complex, Double)->Number
): ComplexArray1D {
    requireSameSize(array1, array2)
    val result = complexZeros(array1.size).asMutable()
    for (i in array1.indices) {
        result[i] = operation(array1[i], array2[i]).toComplex()
    }
    return result
}

inline fun MutableComplexArray1D.applyElementWise(
        operation: (Complex) -> Complex
): MutableComplexArray1D {
    for (i in indices) {
        this[i] = operation(this[i])
    }
    return this
}

inline fun MutableComplexArray1D.applyElementWise(
        other: ComplexArray1D,
        operation: (Complex, Complex) -> Complex
): MutableComplexArray1D {
    requireSameSize(this, other)
    for (i in indices) {
        this[i] = operation(this[i], other[i])
    }
    return this
}

inline fun MutableComplexArray1D.applyElementWise(
        other: DoubleArray1D,
        operation: (Complex, Double) -> Complex
): MutableComplexArray1D {
    requireSameSize(this, other)
    for (i in indices) {
        this[i] = operation(this[i], other[i])
    }
    return this
}