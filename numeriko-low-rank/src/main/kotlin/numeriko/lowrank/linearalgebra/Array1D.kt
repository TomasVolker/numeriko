package numeriko.lowrank.linearalgebra

import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import numeriko.lowrank.interfaces.array1d.double.sumBy
import kotlin.math.abs
import kotlin.math.pow

fun DoubleArray1D.norm(p: Double): Double = sumBy { abs(it).pow(p) }.pow(1.0 / p)

fun linearSpace(start: Double, stop: Double, amount: Int = 10): DoubleArray1D {
    require(0 < amount) {
        "Amount cannot be non positive ($amount <= 0)"
    }
    return doubleArray1D(amount) { i -> start + (stop - start) * (i / (amount - 1.0)) }
}
