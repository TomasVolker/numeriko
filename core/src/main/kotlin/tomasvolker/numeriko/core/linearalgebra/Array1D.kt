package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.functions.norm2
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array1d.generic.isNotEmpty
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sumDouble
import tomasvolker.numeriko.core.primitives.sumInt

fun DoubleArray1D.normalized(): DoubleArray1D = this / this.norm2()

infix fun DoubleArray1D.inner(other: DoubleArray1D): Double {
    requireSameSize(this, other)
    return sumDouble(indices) { i -> this[i] * other[i] }
}

fun linearSpace(start: Double, stop: Double, amount: Int = 10): DoubleArray1D {
    require(0 < amount) {
        "Amount cannot be non positive (${amount} <= 0)"
    }
    return doubleArray1D(amount) { i -> start + (stop - start) * (i / (amount - 1.0)) }
}

fun DoubleArray1D.average(): Double = sum() / size

fun IntArray1D.average(): Double = sum().toDouble() / size

fun DoubleArray1D.cumSum(): DoubleArray1D {
    val result = doubleZeros(this.size).asMutable()
    if (this.isNotEmpty()) {
        result[0] = this[0]
    }
    for (i in 1 until size) {
        result[i] = this[i] + result[i -1]
    }
    return result
}

fun DoubleArray1D.diff(): DoubleArray1D {
    val result = doubleZeros(this.size).asMutable()
    if (this.isNotEmpty()) {
        result[0] = this[0]
    }
    for (i in 1 until size) {
        result[i] = this[i] - this[i -1]
    }
    return result
}

infix fun DoubleArray1D.convolve(other: DoubleArray1D): DoubleArray1D {
    requireSameSize(this, other)

    return doubleArray1D(this.size) { i ->
        sumDouble(other.indices) { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
}

infix fun IntArray1D.convolve(other: IntArray1D): IntArray1D {
    requireSameSize(this, other)

    return intArray1D(this.size) { i ->
        sumInt(other.indices) { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
}


fun DoubleArray1D.filter1D(filter: DoubleArray1D, padding: Double = 0.0): DoubleArray1D {

    val filterCenter = filter.size / 2

    return doubleArray1D(this.size) { i ->
        sumDouble(filter.indices) { j ->
            val k = i - j - filterCenter
            if (k in 0 until this.size) {
                this[k] * filter[j]
            } else {
                padding
            }
        }
    }
}