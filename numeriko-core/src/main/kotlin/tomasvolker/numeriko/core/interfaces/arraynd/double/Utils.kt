package tomasvolker.numeriko.core.interfaces.arraynd.double

import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND0DView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND1DView
import tomasvolker.numeriko.core.interfaces.arraynd.double.view.DefaultDoubleArrayND2DView
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.MutableArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeForEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.generic.unsafeGetView
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros
import tomasvolker.numeriko.core.preconditions.requireSameShape

inline fun elementWise(
        source: DoubleArrayND,
        destination: MutableDoubleArrayND,
        operation: (Double) -> Double
) {
    requireSameShape(source, destination)
    source.unsafeForEachIndices { indices ->
        destination[indices] = operation(source[indices])
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
    source1.unsafeForEachIndices { indices ->
        destination[indices] = operation(source1[indices], source2[indices])
    }
}

inline fun DoubleArrayND.elementWise(operation: (Double) -> Double): DoubleArrayND {
    val result = doubleZeros(shape).asMutable()
    elementWise(
            source = this,
            destination = result,
            operation = operation
    )
    return result
}

inline fun elementWise(array1: DoubleArrayND, array2: DoubleArrayND, operation: (Double, Double) -> Double): DoubleArrayND {
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

fun DoubleArrayND.unsafeGetView(vararg indices: Any): DoubleArrayND =
        (this as ArrayND<Double>).unsafeGetView(*indices) as DoubleArrayND

fun MutableDoubleArrayND.unsafeGetView(vararg indices: Any): MutableDoubleArrayND =
        (this as DoubleArrayND).unsafeGetView(*indices) as MutableDoubleArrayND
