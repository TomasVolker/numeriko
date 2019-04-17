package tomasvolker.core.preconditions

import numeriko.lowrank.interfaces.array2d.generic.Array2D

internal const val rankError2DMessage = "Array is known to be rank 2D in compile time"

fun requireSameShape(array1: Array2D<*>, array2: Array2D<*>) =
        require(array1.shape0 == array2.shape0 && array1.shape1 == array2.shape1) {
            "Arrays must have the same shape (${array1.shape} != ${array2.shape})"
        }

fun requireNotEmpty(array1: Array2D<*>) =
        require(array1.isNotEmpty()) {
            "Array must not be empty"
        }