package tomasvolker.numeriko.core.interfaces.array1d.generic

fun Array1D<*>.defaultEquals(other: Array1D<*>): Boolean {

    if (this.size != other.size)
        return false

    for (i in indices) {
        if (this.getValue(i) != other.getValue(i))
            return false
    }

    return true
}

fun Array1D<*>.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + size.hashCode()
    for (x in this) {
        result += 31 * result + (x?.hashCode() ?: 0)
    }

    return result
}

fun Array1D<*>.defaultToString(): String =
        joinToString(
                separator = ", ",
                prefix = "[ ",
                postfix = " ]"
        )

