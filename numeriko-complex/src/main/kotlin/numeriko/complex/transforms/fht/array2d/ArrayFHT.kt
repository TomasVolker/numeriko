package numeriko.complex.transforms.fht.array2d

import numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.core.index.All
import numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import numeriko.lowrank.interfaces.array2d.double.MutableDoubleArray2D
import numeriko.lowrank.interfaces.array2d.generic.indices0
import numeriko.lowrank.interfaces.array2d.generic.indices1
import tomasvolker.core.interfaces.factory.doubleZeros

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
