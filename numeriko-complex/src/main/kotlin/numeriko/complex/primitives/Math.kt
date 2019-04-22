package numeriko.complex.primitives

import kotlin.math.*

fun Complex.pow(power: Double) =
        polarComplex(this.absoluteValue.pow(power), this.argument * power)

fun Complex.pow(power: Int) =
        polarComplex(this.absoluteValue.pow(power), this.argument * power)

fun Complex.pow(other: Complex) = exp(other * ln(this))

fun Double.pow(other: Complex) = this.toComplex().pow(other)
fun Float.pow(other: Complex) = this.toComplex().pow(other)
fun Long.pow(other: Complex) = this.toComplex().pow(other)
fun Int.pow(other: Complex) = this.toComplex().pow(other)

fun Complex.normalized() = this / this.absoluteValue

fun abs(x: Complex) = x.absoluteValue

fun sqrt(x: Complex) =
        polarComplex(sqrt(x.absoluteValue), x.argument / 2)

fun exp(x: Complex) =
        polarComplex(exp(x.real), x.imag)

fun ln(x: Complex) =
        Complex(ln(x.absoluteValue), x.argument)

fun cos(x: Complex) =
        Complex(cos(x.real) * cosh(x.imag), -sin(x.real) * sinh(x.imag))

fun sin(x: Complex) =
        Complex(sin(x.real) * cosh(x.imag), cos(x.real) * sinh(x.imag))


fun cosh(x: Complex) =
        Complex(cosh(x.real) * cos(x.imag), sinh(x.real) * sin(x.imag))

fun sinh(x: Complex) =
        Complex(sinh(x.real) * cos(x.imag), cosh(x.real) * sin(x.imag))

