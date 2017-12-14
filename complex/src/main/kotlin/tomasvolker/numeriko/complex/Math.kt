package tomasvolker.numeriko.complex

import kotlin.math.*

fun DoubleComplex.pow(power: Double) =
        polarDoubleComplex(this.absoluteValue.pow(power), this.argument * power)

fun DoubleComplex.pow(power: Int) =
        polarDoubleComplex(this.absoluteValue.pow(power), this.argument * power)

fun DoubleComplex.pow(other: DoubleComplex) = exp(other * ln(this))

fun Double.pow(other: DoubleComplex) = this.toDoubleComplex().pow(other)
fun Float.pow(other: DoubleComplex) = this.toDoubleComplex().pow(other)
fun Long.pow(other: DoubleComplex) = this.toDoubleComplex().pow(other)
fun Int.pow(other: DoubleComplex) = this.toDoubleComplex().pow(other)

fun DoubleComplex.normalized() = this / this.absoluteValue

fun abs(x: DoubleComplex) = x.absoluteValue

fun sqrt(x: DoubleComplex) =
        polarDoubleComplex(sqrt(x.absoluteValue), x.argument / 2)

fun exp(x: DoubleComplex) =
        polarDoubleComplex(exp(x.real), x.imag)

fun ln(x: DoubleComplex) =
        DoubleComplex(ln(x.absoluteValue), x.argument)

fun cos(x: DoubleComplex) =
        DoubleComplex(cos(x.real) * cosh(x.imag), -sin(x.real) * sinh(x.imag))

fun sin(x: DoubleComplex) =
        DoubleComplex(sin(x.real) * cosh(x.imag), cos(x.real) * sinh(x.imag))


fun cosh(x: DoubleComplex) =
        DoubleComplex(cosh(x.real) * cos(x.imag), sinh(x.real) * sin(x.imag))

fun sinh(x: DoubleComplex) =
        DoubleComplex(sinh(x.real) * cos(x.imag), cosh(x.real) * sin(x.imag))

