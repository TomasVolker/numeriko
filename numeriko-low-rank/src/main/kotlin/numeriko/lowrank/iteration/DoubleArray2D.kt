package tomasvolker.core.interfaces.iteration

import numeriko.lowrank.interfaces.array2d.double.DoubleArray2D

inline fun DoubleArray2D.inlinedForEachIndexed(function: (i0: Int, i1: Int, value: Double)->Unit) {
    val iterator = arrayIterator()
    while (iterator.hasNext()) {
        val value = iterator.nextDouble()
        function(iterator.i0, iterator.i1, value)
    }
}

inline fun DoubleArray2D.inlinedElementWise(function: (Double)->Double): DoubleArray2D {
    val result = doubleZeros(shape0, shape1).asMutable()
    this.inlinedForEachIndexed { i0, i1, value ->
        result.setDouble(i0, i1, function(value))
    }
    return result
}
