package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.elementWise
import tomasvolker.numeriko.core.interfaces.array1d.integer.sumBy
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import tomasvolker.numeriko.core.preconditions.requireSameSize
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sumInt

operator fun IntArray1D.unaryPlus(): IntArray1D = this
operator fun IntArray1D.unaryMinus(): IntArray1D = elementWise { -it }

operator fun IntArray1D.plus (other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t + o }
operator fun IntArray1D.minus(other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t - o }
operator fun IntArray1D.times(other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t * o }
operator fun IntArray1D.div  (other: IntArray1D): IntArray1D = elementWise(this, other) { t, o -> t / o }

operator fun IntArray1D.plus (other: Int): IntArray1D = elementWise { it + other }
operator fun IntArray1D.minus(other: Int): IntArray1D = elementWise { it - other }
operator fun IntArray1D.times(other: Int): IntArray1D = elementWise { it * other }
operator fun IntArray1D.div  (other: Int): IntArray1D = elementWise { it / other }

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