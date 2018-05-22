package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.sumBy
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray1D
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

