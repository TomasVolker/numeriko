package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import java.lang.IllegalArgumentException

fun DoubleArray1D.argmax(): Int {
    var resultIndex = 0
    var resultValue = Double.NEGATIVE_INFINITY

    forEachIndex { i ->
        val value = this[i]
        if (value > resultValue) {
            resultIndex = i
            resultValue = value
        }
    }
    return resultIndex
}

fun DoubleArray2D.reduceArgmax(axis: Int = 0): IntArray1D = when(axis) {
    0 -> intArray1D(shape1) { i -> this[All, i].argmax() }
    1 -> intArray1D(shape0) { i -> this[i, All].argmax() }
    else -> throw IllegalArgumentException("axis out of bounds")
}
