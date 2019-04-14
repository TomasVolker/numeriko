package tomasvolker.numeriko.lowrank.interfaces.array1d.double

import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.indices

fun DoubleArray1D.defaultEquals(other: DoubleArray1D): Boolean {

    if (this.size != other.size)
        return false

    for (i in indices) {
        if (this.getDouble(i) != other.getDouble(i))
            return false
    }

    return true
}

fun DoubleArray1D.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + size.hashCode()
    for (x in this) {
        result += 31 * result + x.hashCode()
    }

    return result
}

