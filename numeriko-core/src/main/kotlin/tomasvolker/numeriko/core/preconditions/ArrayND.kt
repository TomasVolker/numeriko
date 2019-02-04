package tomasvolker.numeriko.core.preconditions

import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import java.lang.IllegalArgumentException

fun requireSameShape(array1: ArrayND<*>, array2: ArrayND<*>) =
        require(array1.shape == array2.shape) {
            "Arrays must have the same shape (${array1.shape} != ${array2.shape})"
        }

fun rankError(target: Int, actual: Int): Nothing =
        throw IllegalArgumentException(
                "Array of rank ${if (actual < 0) "N" else actual.toString()} cannot be viewed as rank $target"
        )
