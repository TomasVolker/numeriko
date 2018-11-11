package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultEquals
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultHashCode
import tomasvolker.numeriko.core.interfaces.array1d.generic.defaultToString
import tomasvolker.numeriko.core.preconditions.requireSameShape

fun defaultEquals(array1: Array2D<*>, array2: Array2D<*>): Boolean {

    if(array1.shape0 != array2.shape0 || array1.shape1 != array2.shape1)
        return false

    array1.forEachIndex { i0, i1 ->
        if (array1.getValue(i0, i1) != array2.getValue(i0, i1))
            return false
    }

    return true
}

fun defaultHashCode(array1: Array2D<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.shape.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun defaultToString(array: Array2D<*>) = buildString {

    append("[")

    for (i0 in array.indices(0)) {

        if (i0 > 0) {
            append(",\n ")
        }

        append(
                array.getView(i0, All).joinToString(
                    separator = ", ",
                    prefix = "[ ",
                    postfix = " ]",
                    limit = 20,
                    truncated = "..."
            )
        )

    }

    append("]")

}




class DefaultArray2DIterator<T>(
        val array: Array2D<T>
): Iterator<T> {

    var i0 = 0
    var i1 = 0

    override fun hasNext(): Boolean =
            i0 < array.shape0 && i1 < array.shape1

    override fun next(): T {
        val result = array.getValue(i0, i1)
        i1++
        if (i1 == array.shape1) {
            i1 = 0
            i0++
        }
        return result
    }

}

