package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun ArrayND<*>.requireValidIndices(indices: IntArray) {
    require(indices.size == rank) {
        "Indides size (${indices.size}) is not equal to the rank (${rank})"
    }
}

fun requireSameShape(array1: ArrayND<*>, array2: ArrayND<*>) =
        require(array1.shape == array2.shape) {
            "Arrays must have the same shape (${array1.shape} != ${array2.shape})"
        }

