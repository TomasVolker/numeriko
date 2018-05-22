package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.sumBy
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleZeros
import tomasvolker.numeriko.core.modulo
import kotlin.math.*

infix fun DoubleArray1D.inner(other: DoubleArray1D): Double {

    require(this.size == other.size) {
        "Sizes must much"
    }

    var result = 0.0
    for (i in indices) {
        result += this[i] * other[i]
    }
    return result
}

fun linearSpace(start: Double, stop: Double, amount: Int = 10): MutableDoubleArray1D {
    require(amount > 0) {
        "amount cannot be non positive"
    }
    return mutableDoubleArray1D(amount) { i -> start + (stop - start) * (i / (amount - 1.0)) }
}

fun DoubleArray1D.norm1(): Double = sumBy { abs(it) }

fun DoubleArray1D.norm2(): Double = sumBy { it * it }

fun DoubleArray1D.norm(p: Double): Double = sumBy { abs(it).pow(p) }

fun DoubleArray1D.average(): Double = sum() / size

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
    require(this.size == other.size) {
        "Sizes must much"
    }

    val result = mutableDoubleZeros(this.size)
    for (i in this.indices) {
        result[i] = (other.indices).sumByDouble { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
    return result
}
