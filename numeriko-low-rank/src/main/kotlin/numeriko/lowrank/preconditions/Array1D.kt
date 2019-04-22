package tomasvolker.core.preconditions

import numeriko.lowrank.interfaces.array1d.generic.Array1D
import numeriko.lowrank.interfaces.array1d.generic.isNotEmpty

internal const val rankError1DMessage = "Array is known to be rank 1D in compile time"

fun requireSameSize(array1: Array1D<*>, array2: Array1D<*>) =
        require(array1.size == array2.size) {
            "Arrays must have the same size (${array1.size} != ${array2.size})"
        }

fun requireNotEmpty(array1: Array1D<*>) =
        require(array1.isNotEmpty()) {
            "Array must not be empty"
        }