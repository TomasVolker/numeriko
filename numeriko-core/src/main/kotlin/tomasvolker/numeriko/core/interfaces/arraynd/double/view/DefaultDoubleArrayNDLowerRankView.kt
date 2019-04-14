package tomasvolker.numeriko.core.interfaces.arraynd.double.view

import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.MutableDoubleArrayND

fun defaultDoubleArrayNDLowerRankView(
        array: MutableDoubleArrayND,
        axis: Int
): MutableDoubleArrayND {
    val colapsedAxis = axis

    require(array.shape[axis] <= 1)

    return doubleArrayNDView(array, array.shape.remove(axis)) { source, target ->

        source.forEachIndexed { axis, index ->
            target[axis] = when {
                axis < colapsedAxis -> source[axis]
                axis == colapsedAxis -> 1
                else -> source[axis - 1]
            }
        }

    }
}
