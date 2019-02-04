package tomasvolker.numeriko.core.interfaces.array2d.generic

import tomasvolker.numeriko.core.index.All

fun Array2D<*>.defaultEquals(other: Array2D<*>): Boolean {

    if(this.shape0 != other.shape0 || this.shape1 != other.shape1)
        return false

    forEachIndex { i0, i1 ->
        if (this.getValue(i0, i1) != other.getValue(i0, i1))
            return false
    }

    return true
}

fun Array2D<*>.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
    for (x in this) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun Array2D<*>.defaultToString() = buildString {

    append("[")

    for (i0 in indices(0)) {

        if (i0 > 0) {
            append(",\n ")
        }

        append(
                getView(i0, All).joinToString(
                        separator = ", ",
                        prefix = "[ ",
                        postfix = " ]"
                )
        )

    }

    append("]")

}




