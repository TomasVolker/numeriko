package tomasvolker.numeriko.lowrank.interfaces.array1d.double.view

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D

fun defaultDoubleArray1DView (
        array: MutableDoubleArray1D,
        offset: Int,
        size: Int,
        stride: Int
): MutableDoubleArray1D {

    array.requireValidIndices(offset)
    array.requireValidIndices(offset + stride * (size - 1))

    return doubleArray1DView(array, size) { i0 -> offset + stride * i0 }
}

fun defaultDoubleArray1DView(array: MutableDoubleArray1D, i0: IntProgression) =
        defaultDoubleArray1DView(
                array = array,
                offset = i0.first,
                size = i0.count(),
                stride = i0.step
        )
