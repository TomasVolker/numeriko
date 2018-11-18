package tomasvolker.numeriko.core.interfaces.array2d.double


import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape
import tomasvolker.numeriko.core.primitives.numericEqualsTo

inline fun elementWise(
        source: DoubleArray2D,
        destination: MutableDoubleArray2D,
        operation: (Double) -> Double
) {
    requireSameShape(source, destination)
    source.forEachIndex { i0, i1 ->
            destination[i0, i1] = operation(source[i0, i1])
    }
}

inline fun elementWise(
        source1: DoubleArray2D,
        source2: DoubleArray2D,
        destination: MutableDoubleArray2D,
        operation: (Double, Double) -> Double
) {
    requireSameShape(source1, source2)
    requireSameShape(source1, destination)
    source1.forEachIndex { i0, i1 ->
        destination[i0, i1] = operation(source1[i0, i1], source2[i0, i1])
    }
}

inline fun DoubleArray2D.elementWise(
        operation: (Double) -> Double
): DoubleArray2D {
    val result = doubleZeros(shape0, shape1).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun MutableDoubleArray2D.applyElementWise(
        operation: (Double) -> Double
): MutableDoubleArray2D {
    elementWise(
            source = this,
            destination = this,
            operation = operation
    )
    return this
}

inline fun elementWise(
        array1: DoubleArray2D,
        array2: DoubleArray2D,
        operation: (Double, Double
        ) -> Double): DoubleArray2D {
    val result = doubleZeros(array1.shape0, array2.shape1).asMutable()
    elementWise(
            source1 = array1,
            source2 = array2,
            destination = result,
            operation = operation
    )
    return result

}

inline fun MutableDoubleArray2D.applyElementWise(
        other: DoubleArray2D,
        operation: (Double, Double) -> Double
): MutableDoubleArray2D {
    requireSameShape(this, other)
    elementWise(
            source1 = this,
            source2 = other,
            destination = this,
            operation = operation
    )
    return this
}

inline fun DoubleArray2D.sumBy(operation: (Double) -> Double): Double {
    var result = 0.0
    forEachIndex { i0, i1 ->
        result += operation(this[i0, i1])
    }
    return result
}

inline fun DoubleArray2D.numericEquals(
        tolerance: Double = NumerikoConfig.defaultTolerance,
        selector: (i0: Int, i1: Int)->Double
): Boolean {
    forEachIndex { i0, i1 ->
        if (!this[i0, i1].numericEqualsTo(selector(i0, i1), tolerance))
            return false
    }
    return true
}