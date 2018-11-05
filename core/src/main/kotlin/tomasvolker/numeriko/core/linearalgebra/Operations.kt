package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.factory.*
import tomasvolker.numeriko.core.primitives.modulo

infix fun DoubleArray2D.matMul(other: DoubleArray1D): DoubleArray1D {
    require(this.shape1 == other.shape0) {
        "sizes dont match"
    }
    return doubleArray1D(this.shape0) { i0 ->
        this.indices1.sumByDouble { k -> this[i0, k] * other[k] }
    }
}

infix fun DoubleArray2D.matMul(other: DoubleArray2D): DoubleArray2D {
    require(this.shape1 == other.shape0) {
        "sizes dont match"
    }
    return doubleArray2D(this.shape0, other.shape1) { i0, i1 ->
        this.indices0.sumByDouble { k -> this[i0, k] * other[k, i1] }
    }
}

infix fun DoubleArray1D.inner(other: DoubleArray1D): Double {
    requireSameSize(this, other)
    return indices.sumByDouble { i -> this[i] * other[i] }
}

fun linearSpace(start: Double, stop: Double, amount: Int = 10): MutableDoubleArray1D {
    require(0 < amount) {
        "Amount cannot be non positive (${amount} <= 0)"
    }
    return mutableDoubleArray1D(amount) { i -> start + (stop - start) * (i / (amount - 1.0)) }
}

fun DoubleArray1D.average(): Double = sum() / size

fun IntArray1D.average(): Double = sum().toDouble() / size

fun DoubleArray1D.cumSum(): MutableDoubleArray1D {
    val result = mutableDoubleZeros(this.size)
    if (this.isNotEmpty()) {
        result[0] = this[0]
    }
    for (i in 1 until size) {
        result[i] = this[i] + result[i -1]
    }
    return result
}

fun DoubleArray1D.diff(): MutableDoubleArray1D {
    val result = mutableDoubleZeros(this.size)
    if (this.isNotEmpty()) {
        result[0] = this[0]
    }
    for (i in 1 until size) {
        result[i] = this[i] - this[i -1]
    }
    return result
}

infix fun DoubleArray1D.convolve(other: DoubleArray1D): MutableDoubleArray1D {
    requireSameSize(this, other)

    val result = mutableDoubleZeros(this.size)
    for (i in this.indices) {
        result[i] = (other.indices).sumByDouble { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
    return result
}

infix fun IntArray1D.convolve(other: IntArray1D): MutableIntArray1D {
    requireSameSize(this, other)

    val result = mutableIntZeros(this.size)
    for (i in this.indices) {
        result[i] = (other.indices).sumBy { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
    return result
}
