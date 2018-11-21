package tomasvolker.numeriko.complex

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

val I = Complex(0.0, 1.0)
val J = I

class Complex(
        val real: Double = 0.0,
        val imag: Double = 0.0
): Number() {

    val imaginary: Double get() = imag

    val absoluteValue: Double get() = hypot(real, imag)

    val argument: Double get() = atan2(imag, real)

    val absoluteValueSquared: Double get() = real * real + imag * imag

    val pureReal get(): Boolean = imag == 0.0

    val pureImaginary get(): Boolean = real == 0.0

    fun conjugate() = Complex(real, -imag)

    operator fun component1() = real

    operator fun component2() = imag

    fun toPair() = Pair(real, imag)

    //plus

    operator fun unaryPlus() = this

    operator fun plus(other: Complex) = Complex(this.real + other.real, this.imag + other.imag)

    operator fun plus(other: Double) = Complex(this.real + other, this.imag)

    operator fun plus(other: Float) = Complex(this.real + other, this.imag)

    operator fun plus(other: Long) = Complex(this.real + other, this.imag)

    operator fun plus(other: Int) = Complex(this.real + other, this.imag)

    //minus

    operator fun unaryMinus() = Complex(-real, -imag)

    operator fun minus(other: Complex) = Complex(this.real - other.real, this.imag - other.imag)

    operator fun minus(other: Double) = Complex(this.real - other, this.imag)

    operator fun minus(other: Float) = Complex(this.real - other, this.imag)

    operator fun minus(other: Long) = Complex(this.real - other, this.imag)

    operator fun minus(other: Int) = Complex(this.real - other, this.imag)

    //times

    operator fun times(other: Complex) =
            Complex(
                    this.real * other.real - this.imag * other.imag,
                    this.real * other.imag + this.imag * other.real)

    operator fun times(other: Double) =
            Complex(
                    this.real * other,
                    this.imag * other)

    operator fun times(other: Float) = times(other.toDouble())


    operator fun times(other: Long) = times(other.toDouble())

    operator fun times(other: Int) = times(other.toDouble())

    //div

    operator fun div(other: Complex): Complex {
        val otherAbsSquared = other.absoluteValueSquared
        return Complex(
                (this.real * other.real + this.imag * other.imag) / otherAbsSquared,
                (-this.real * other.imag + this.imag * other.real) / otherAbsSquared
        )
    }

    operator fun div(other: Double) = Complex(this.real / other, this.imag / other)

    operator fun div(other: Float) = div(other.toDouble())

    operator fun div(other: Long) = div(other.toDouble())

    operator fun div(other: Int) = div(other.toDouble())

    override fun equals(other: Any?) =
            when(other) {
                is Complex -> this.real == other.real && this.imag == other.imag
                is Number -> pureReal && this.real == other
                else -> false
            }

    override fun hashCode() = 31 * real.hashCode() + imag.hashCode()

    override fun toString() = "($real, $imag)"

    override fun toByte(): Byte = toDouble().toByte()
    override fun toChar(): Char = toDouble().toChar()
    override fun toDouble(): Double = real
    override fun toFloat(): Float = toDouble().toFloat()
    override fun toInt(): Int = toDouble().toInt()
    override fun toLong(): Long = toDouble().toLong()
    override fun toShort(): Short = toDouble().toShort()

}

fun polarComplex(absoluteValue: Double, argument: Double = 0.0) =
        Complex(absoluteValue * cos(argument), absoluteValue * sin(argument))

fun cartesianComplex(real: Double, imaginary: Double = 0.0) =
        Complex(real, imaginary)

fun unaryDoubleComplex(argument: Double = 0.0) = Complex(cos(argument), sin(argument))

operator fun Double.plus(other: Complex) = Complex(this + other.real, other.imag)
operator fun Float.plus(other: Complex) = this.toDouble() + other
operator fun Long.plus(other: Complex) = this.toDouble() + other
operator fun Int.plus(other: Complex) = this.toDouble() + other

operator fun Double.minus(other: Complex) = Complex(this - other.real, -other.imag)
operator fun Float.minus(other: Complex) = this.toDouble() - other
operator fun Long.minus(other: Complex) = this.toDouble() - other
operator fun Int.minus(other: Complex) = this.toDouble() - other


operator fun Double.times(other: Complex) = Complex(this * other.real, this * other.imag)
operator fun Float.times(other: Complex) = this.toDouble() * other
operator fun Long.times(other: Complex) = this.toDouble() * other
operator fun Int.times(other: Complex) = this.toDouble() * other


operator fun Double.div(other: Complex): Complex {
    val otherAbsSquared = other.absoluteValueSquared
    return Complex(
            this * other.real / otherAbsSquared,
            -this * other.imag / otherAbsSquared)
}
operator fun Float.div(other: Complex) = this.toDouble() / other
operator fun Long.div(other: Complex) = this.toDouble() / other
operator fun Int.div(other: Complex) = this.toDouble() / other

fun Number.toComplex() = when(this) {
    is Complex -> this
    else -> this.toDouble().toComplex()
}
fun Double.toComplex() = Complex(this, 0.0)
fun Float.toComplex() = this.toDouble().toComplex()
fun Long.toComplex() = this.toDouble().toComplex()
fun Int.toComplex() = this.toDouble().toComplex()

val Double.i get() = Complex(0.0, this)
val Float.i get() = this.toDouble().i
val Long.i get() = this.toDouble().i
val Int.i get() = this.toDouble().i

val Double.j get() = Complex(0.0, this)
val Float.j get() = this.toDouble().j
val Long.j get() = this.toDouble().j
val Int.j get() = this.toDouble().j
