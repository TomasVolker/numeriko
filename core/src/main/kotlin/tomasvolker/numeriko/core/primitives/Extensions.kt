package tomasvolker.numeriko.core.primitives

infix fun Int.modulo(other: Int) = ((this % other) + other) % other
infix fun Long.modulo(other: Long) = ((this % other) + other) % other
infix fun Float.modulo(other: Float) = ((this % other) + other) % other
infix fun Double.modulo(other: Double) = ((this % other) + other) % other


fun Int.squared(): Int = this * this
fun Long.squared(): Long = this * this
fun Float.squared(): Float = this * this
fun Double.squared(): Double = this * this

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

