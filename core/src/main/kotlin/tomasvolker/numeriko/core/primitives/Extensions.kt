package tomasvolker.numeriko.core.primitives

import tomasvolker.numeriko.core.config.NumerikoConfig
import kotlin.math.abs

infix fun Int.modulo(other: Int) = ((this % other) + other) % other
infix fun Long.modulo(other: Long) = ((this % other) + other) % other
infix fun Float.modulo(other: Float) = ((this % other) + other) % other
infix fun Double.modulo(other: Double) = ((this % other) + other) % other

fun Int.squared(): Int = this * this
fun Long.squared(): Long = this * this
fun Float.squared(): Float = this * this
fun Double.squared(): Double = this * this

fun Int.cubed(): Int = this * this * this
fun Long.cubed(): Long = this * this * this
fun Float.cubed(): Float = this * this * this
fun Double.cubed(): Double = this * this * this

infix fun Double.numericEqualsTo(
        other: Double
): Boolean =
        numericEqualsTo(other, NumerikoConfig.defaultTolerance)

fun Double.numericEqualsTo(
        other: Double,
        tolerance: Double
): Boolean =
        abs(other - this) <= tolerance

fun sqrt(value: Int): Float = kotlin.math.sqrt(value.toFloat())
fun sqrt(value: Long): Double = kotlin.math.sqrt(value.toDouble())

fun Boolean.indicative() = if(this) 1.0 else 0.0

fun pow2(power: Int): Int {
    var x = 1
    repeat(power){
        x *= 2
    }
    return x
}

tailrec fun Int.isPowerOf2(): Boolean = when {
    this == 0 -> false
    this == 1 -> true
    else ->
        if (this % 2 != 0)
            false
        else
            (this / 2).isPowerOf2()

}

fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = this % 2 == 1

fun Long.isEven(): Boolean = this % 2 == 0L
fun Long.isOdd(): Boolean = this % 2 == 1L

inline fun sumDouble(indices: IntProgression, value: (i: Int)->Double): Double {
    var result = 0.0
    for (i in indices) {
        result += value(i)
    }
    return result
}

inline fun sumDouble(
        indices0: IntProgression,
        indices1: IntProgression,
        value: (i0: Int, i1: Int)->Double
): Double {
    var result = 0.0
    for (i0 in indices0) {
        for (i1 in indices1) {
            result += value(i0, i1)
        }
    }
    return result
}

inline fun sumInt(indices: IntProgression, value: (i: Int)->Int): Int =
        indices.sumBy(value)

inline fun sumInt(
        indices0: IntProgression,
        indices1: IntProgression,
        value: (i0: Int, i1: Int)->Int
): Int =
        indices0.sumBy { i0 ->
            indices1.sumBy { i1 ->
                value(i0, i1)
            }
        }
