package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.intArray1D

fun defaultDoubleArrayNDView(
        array: MutableDoubleArrayND,
        offset: IntArray1D,
        shape: IntArray1D,
        stride: IntArray1D
): MutableDoubleArrayND {

    require(array.rank == offset.size &&
            array.rank == shape.size &&
            array.rank == stride.size
    )

    return doubleArrayNDView(array, shape) { source, target ->

        source.forEachIndexed { axis, index ->
            target[axis] = offset[axis] + stride[axis] * index
        }

    }
}

fun defaultDoubleArrayNDView(array: MutableDoubleArrayND, indices: Array<out IntProgression>) =
        defaultDoubleArrayNDView(
                array = array,
                offset = intArray1D(indices.size) { i -> indices[i].first },
                shape = intArray1D(indices.size) { i -> indices[i].count() },
                stride = intArray1D(indices.size) { i -> indices[i].step }
        )
