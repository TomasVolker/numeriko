package tomasvolker.numeriko.complex

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

val I = DoubleComplex(0.0, 1.0)
val J = I

class DoubleComplex(
        val real: Double = 0.0,
        val imag: Double = 0.0) {

    val imaginary: Double get() = imag

    val absoluteValue: Double get() = hypot(real, imag)

    val argument: Double get() = atan2(imag, real)

    val absoluteValueSquared: Double get() = real * real + imag * imag

    val pureReal get(): Boolean = imag == 0.0

    val pureImaginary get(): Boolean = real == 0.0

    fun conjugate() = DoubleComplex(real, -imag)

    operator fun component1() = real

    operator fun component2() = imag

    fun toPair() = Pair(real, imag)

    //plus

    operator fun unaryPlus() = this

    operator fun plus(other: DoubleComplex) = DoubleComplex(this.real + other.real, this.imag + other.imag)

    operator fun plus(other: Double) = DoubleComplex(this.real + other, this.imag)

    operator fun plus(other: Float) = DoubleComplex(this.real + other, this.imag)

    operator fun plus(other: Long) = DoubleComplex(this.real + other, this.imag)

    operator fun plus(other: Int) = DoubleComplex(this.real + other, this.imag)

    //minus

    operator fun unaryMinus() = DoubleComplex(-real, -imag)

    operator fun minus(other: DoubleComplex) = DoubleComplex(this.real - other.real, this.imag - other.imag)

    operator fun minus(other: Double) = DoubleComplex(this.real - other, this.imag)

    operator fun minus(other: Float) = DoubleComplex(this.real - other, this.imag)

    operator fun minus(other: Long) = DoubleComplex(this.real - other, this.imag)

    operator fun minus(other: Int) = DoubleComplex(this.real - other, this.imag)

    //times

    operator fun times(other: DoubleComplex) =
            DoubleComplex(
                    this.real * other.real - this.imag * other.imag,
                    this.real * other.imag + this.imag * other.real)

    operator fun times(other: Double) =
            DoubleComplex(
                    this.real * other,
                    this.imag * other)

    operator fun times(other: Float) = times(other.toDouble())


    operator fun times(other: Long) = times(other.toDouble())

    operator fun times(other: Int) = times(other.toDouble())

    //div

    operator fun div(other: DoubleComplex): DoubleComplex {
        val otherAbsSquared = other.absoluteValueSquared
        return DoubleComplex(
                (this.real * other.real + this.imag * other.imag) / otherAbsSquared,
                (-this.real * other.imag + this.imag * other.real) / otherAbsSquared)
    }

    operator fun div(other: Double) = DoubleComplex(this.real / other, this.imag / other)

    operator fun div(other: Float) = div(other.toDouble())

    operator fun div(other: Long) = div(other.toDouble())

    operator fun div(other: Int) = div(other.toDouble())

    override fun equals(other: Any?) =
            when(other) {
                is DoubleComplex -> this.real == other.real && this.imag == other.imag
                is Number -> pureReal && this.real == other
                else -> false
            }

    override fun hashCode() = 31 * real.hashCode() + imag.hashCode()

    override fun toString() = "($real, $imag)"

}

fun polarDoubleComplex(absoluteValue: Double, argument: Double = 0.0) =
        DoubleComplex(absoluteValue * cos(argument), absoluteValue * sin(argument))

fun cartesianDoubleComplex(real: Double, imaginary: Double = 0.0) =
        DoubleComplex(real, imaginary)

fun unaryDoubleComplex(argument: Double = 0.0) = DoubleComplex(cos(argument), sin(argument))

operator fun Double.plus(other: DoubleComplex) = DoubleComplex(this + other.real, other.imag)
operator fun Float.plus(other: DoubleComplex) = this.toDouble() + other
operator fun Long.plus(other: DoubleComplex) = this.toDouble() + other
operator fun Int.plus(other: DoubleComplex) = this.toDouble() + other

operator fun Double.minus(other: DoubleComplex) = DoubleComplex(this - other.real, -other.imag)
operator fun Float.minus(other: DoubleComplex) = this.toDouble() - other
operator fun Long.minus(other: DoubleComplex) = this.toDouble() - other
operator fun Int.minus(other: DoubleComplex) = this.toDouble() - other


operator fun Double.times(other: DoubleComplex) = DoubleComplex(this * other.real, this * other.imag)
operator fun Float.times(other: DoubleComplex) = this.toDouble() * other
operator fun Long.times(other: DoubleComplex) = this.toDouble() * other
operator fun Int.times(other: DoubleComplex) = this.toDouble() * other


operator fun Double.div(other: DoubleComplex): DoubleComplex {
    val otherAbsSquared = other.absoluteValueSquared
    return DoubleComplex(
            this * other.real / otherAbsSquared,
            -this * other.imag / otherAbsSquared)
}
operator fun Float.div(other: DoubleComplex) = this.toDouble() / other
operator fun Long.div(other: DoubleComplex) = this.toDouble() / other
operator fun Int.div(other: DoubleComplex) = this.toDouble() / other

fun Double.toDoubleComplex() = DoubleComplex(this, 0.0)
fun Float.toDoubleComplex() = this.toDouble().toDoubleComplex()
fun Long.toDoubleComplex() = this.toDouble().toDoubleComplex()
fun Int.toDoubleComplex() = this.toDouble().toDoubleComplex()

val Double.i get() = DoubleComplex(0.0, this)
val Float.i get() = this.toDouble().i
val Long.i get() = this.toDouble().i
val Int.i get() = this.toDouble().i

val Double.j get() = DoubleComplex(0.0, this)
val Float.j get() = this.toDouble().j
val Long.j get() = this.toDouble().j
val Int.j get() = this.toDouble().j
