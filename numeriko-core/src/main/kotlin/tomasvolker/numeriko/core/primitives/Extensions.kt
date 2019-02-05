package tomasvolker.numeriko.core.primitives

import tomasvolker.numeriko.core.config.NumerikoConfig
import kotlin.math.abs
import kotlin.math.sqrt

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

fun sqrt(value: Int): Float = sqrt(value.toFloat())
fun sqrt(value: Long): Double = sqrt(value.toDouble())

fun Boolean.indicator() = if(this) 1.0 else 0.0

fun pow2(power: Int): Int = 1 shl power

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
