package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D

fun requireSameShape(array1: Array2D<*>, array2: Array2D<*>) =
        require(array1.shape == array2.shape) {
            "Arrays must have the same shape (${array1.shape} != ${array2.shape})"
        }

fun requireNotEmpty(array1: Array2D<*>) =
        require(array1.isNotEmpty()) {
            "Array must not be empty"
        }