package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.lowrank.interfaces.array1d.generic.indices
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.elementWise
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.sumBy

fun IntArray1D.sum(): Int = sumBy { it }

fun IntArray1D.average(): Double = sum().toDouble() / size

infix fun IntArray1D.convolve(other: IntArray1D): IntArray1D {
    requireSameSize(this, other)
    return intArray1D(this.size) { i ->
        sumInt(other.indices) { j ->
            this[(i - j) modulo size] * other[j]
        }
    }
}