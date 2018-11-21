package tomasvolker.numeriko.complex.interfaces.array2d

import tomasvolker.numeriko.complex.primitives.Complex
import tomasvolker.numeriko.complex.interfaces.factory.complexZeros
import tomasvolker.numeriko.complex.primitives.j
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: ComplexArray2D,
        destination: MutableComplexArray2D,
        operation: (Complex) -> Complex
) {
    requireSameShape(source, destination)
    source.forEachIndex { i0, i1 ->
        destination[i0, i1] = operation(source[i0, i1])
    }
}

inline fun elementWise(
        source1: ComplexArray2D,
        source2: ComplexArray2D,
        destination: MutableComplexArray2D,
        operation: (Complex, Complex) -> Complex
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndex { i0, i1 ->
        destination[i0, i1] = operation(source1[i0, i1], source2[i0, i1])
    }
}

inline fun ComplexArray2D.elementWise(
        operation: (Complex) -> Complex
): ComplexArray2D {
    val result = complexZeros(shape0, shape1).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableComplexArray2D.applyElementWise(
        operation: (Complex) -> Complex
): MutableComplexArray2D {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(
        array1: ComplexArray2D,
        array2: ComplexArray2D,
        operation: (Complex, Complex
        ) -> Complex): ComplexArray2D {
    val result = complexZeros(array1.shape0, array2.shape1).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableComplexArray2D.applyElementWise(
        other: ComplexArray2D,
        operation: (Complex, Complex) -> Complex
): MutableComplexArray2D {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

inline fun ComplexArray2D.sumBy(operation: (Complex) -> Complex): Complex {
    var result = 0.0.j
    forEachIndex { i0, i1 ->
        result += operation(this[i0, i1])
    }
    return result
}
