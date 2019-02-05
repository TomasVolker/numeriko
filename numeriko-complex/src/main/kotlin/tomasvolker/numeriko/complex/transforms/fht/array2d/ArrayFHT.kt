package tomasvolker.numeriko.complex.transforms.fht.array2d

import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.functions.applyTimes
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros

fun fht(
        array: DoubleArray2D,
        destination: MutableDoubleArray2D = doubleZeros(array.shape0, array.shape1).asMutable()
): DoubleArray2D {

    val horizontalFht = doubleZeros(array.shape0, array.shape1).asMutable()

    for (i0 in array.indices0) {
        fht(array[i0, All], horizontalFht[i0, All])
    }

    for (i1 in array.indices1) {
        fht(horizontalFht[All, 0], destination[All, i1])
    }

    return destination.applyTimes(array.size)
}
