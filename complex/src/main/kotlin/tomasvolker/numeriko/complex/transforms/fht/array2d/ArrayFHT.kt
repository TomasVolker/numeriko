package tomasvolker.numeriko.complex.transforms.fht.array2d

import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros

fun fht(
        array: DoubleArray2D,
        destination: MutableDoubleArray2D = mutableDoubleZeros(array.shape0, array.shape1)
): DoubleArray2D {

    val horizontalFht = mutableDoubleZeros(array.shape0, array.shape1)

    for (i0 in array.indices0) {
        fht(array.getView(0, All), horizontalFht.getView(0, All))
    }

    for (i1 in array.indices1) {
        fht(horizontalFht.getView(All, 0), destination.getView(All, 0))
    }

    return destination.applyTimes(array.size)
}
