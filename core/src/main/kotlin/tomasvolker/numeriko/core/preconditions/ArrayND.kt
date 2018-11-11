package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun ArrayND<*>.requireValidIndices(indices: IntArray) {
    require(indices.size == rank) {
        "Indides size (${indices.size}) is not equal to the rank (${rank})"
    }
}