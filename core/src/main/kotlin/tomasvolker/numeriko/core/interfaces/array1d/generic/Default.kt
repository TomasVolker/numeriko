package tomasvolker.numeriko.core.interfaces.array1d.generic

fun defaultEquals(array1: Array1D<*>, array2: Array1D<*>): Boolean {

    if (array1.size != array2.size)
        return false

    for (i in array1.indices) {
        if (array1.getValue(i) != array2.getValue(i))
            return false
    }

    return true
}

fun defaultHashCode(array1: Array1D<*>): Int {

    var result = array1.rank.hashCode()
    result += 31 * result + array1.size.hashCode()
    for (x in array1) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun defaultToString(array1: Array1D<*>): String =
        array1.joinToString(
                separator = ", ",
                prefix = "[ ",
                postfix = " ]",
                limit = 20,
                truncated = "..."
        )


class DefaultArray1DIterator<T>(
        val array: Array1D<T>,
        initialIndex: Int = 0
): ListIterator<T> {

    private var index = initialIndex

    override fun hasNext(): Boolean =
            index < array.size

    override fun next(): T =
            array.getValue(index).apply { index++ }

    override fun hasPrevious(): Boolean =
            0 < index

    override fun nextIndex(): Int = index

    override fun previous(): T {
        index--
        return array.getValue(index)
    }

    override fun previousIndex(): Int = index - 1

}
