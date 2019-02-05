package tomasvolker.numeriko.core.interfaces.iteration

import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.factory.array2DOfNulls

inline fun <T> Array2D<T>.inlinedForEachIndexed(function: (i0: Int, i1: Int, value: T)->Unit) {
    val iterator = arrayIterator()
    while (iterator.hasNext()) {
        val value = iterator.next()
        function(iterator.i0, iterator.i1, value)
    }
}

inline fun <T> Array2D<T>.inlinedElementWise(function: (T)->T): Array2D<T> {
    val result = array2DOfNulls<T>(shape0, shape1).asMutable()
    this.inlinedForEachIndexed { i0, i1, value ->
        result.setValue(i0, i1, function(value))
    }
    return result as Array2D<T>
}
