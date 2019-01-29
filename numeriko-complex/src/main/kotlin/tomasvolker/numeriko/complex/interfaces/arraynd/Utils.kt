package tomasvolker.numeriko.complex.interfaces.arraynd

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.factory.complexZeros
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeGetView
import tomasvolker.numeriko.core.performance.fastForEachIndices
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: ComplexArrayND,
        destination: MutableComplexArrayND,
        operation: (Complex) -> Complex
) {
    requireSameShape(source, destination)
    source.fastForEachIndices { indices ->
        destination.setValue(indices, operation(source.getValue(*indices)))
    }
}

inline fun elementWise(
        source1: ComplexArrayND,
        source2: ComplexArrayND,
        destination: MutableComplexArrayND,
        operation: (Complex, Complex) -> Complex
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndices { indices ->
        destination[indices] = operation(source1[indices], source2[indices])
    }
}

inline fun ComplexArrayND.elementWise(
        operation: (Complex) -> Complex
): ComplexArrayND {
    val result = complexZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun elementWise(
        array1: ComplexArrayND,
        array2: ComplexArrayND,
        operation: (Complex, Complex) -> Complex
): ComplexArrayND {
    requireSameShape(array1, array2)
    val result = complexZeros(array1.shape).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableComplexArrayND.applyElementWise(
        operation: (Complex) -> Complex
): MutableComplexArrayND {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun MutableComplexArrayND.applyElementWise(
        other: ComplexArrayND,
        operation: (Complex, Complex) -> Complex
): MutableComplexArrayND {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

fun ComplexArrayND.unsafeGetView(vararg indices: Any): ComplexArrayND =
        (this as ArrayND<Complex>).unsafeGetView(*indices) as ComplexArrayND

fun MutableComplexArrayND.unsafeGetView(vararg indices: Any): MutableComplexArrayND =
        (this as ComplexArrayND).unsafeGetView(*indices) as MutableComplexArrayND
