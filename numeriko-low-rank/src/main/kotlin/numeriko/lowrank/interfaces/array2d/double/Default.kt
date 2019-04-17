package numeriko.lowrank.interfaces.array2d.double

import numeriko.lowrank.interfaces.array2d.generic.*

fun DoubleArray2D.defaultEquals(other: DoubleArray2D): Boolean {

    if(this.shape0 != other.shape0 || this.shape1 != other.shape1)
        return false

    forEachIndex { i0, i1 ->
        if (this[i0, i1] != other[i0, i1])
            return false
    }

    return true
}

fun DoubleArray2D.defaultHashCode(): Int {

    var result = rank.hashCode()
    result += 31 * result + shape.hashCode()
    for (x in this) {
        result += 31 * result + x.hashCode()
    }

    return result
}

