package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.affine.DefaultAffineFunction
import tomasvolker.numeriko.core.functional.affine.DefaultLinearFunction
import tomasvolker.numeriko.core.functional.affine.LinearFunction
import tomasvolker.numeriko.core.functional.constant.*
import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.function1.Function1
import tomasvolker.numeriko.core.functional.function2.operators.*


fun constant(value: Double): Constant = DefaultConstant(value)
fun constant(value: Int) = constant(value.toLong())
fun constant(value: Long): IntegerConstant = when(value) {
    0L -> Zero
    1L -> One
    -1L -> MinusOne
    else -> DefaultIntegerConstant(value)
}
fun Double.asConstant() = constant(this)
fun Int.asConstant() = constant(this.toLong())
fun Long.asConstant() = constant(this)

fun variable(name: String) = Variable(name)
fun String.asVariable() = Variable(this)

operator fun Int.plus(function: Function1) = asConstant() + function
operator fun Int.minus(function: Function1) = asConstant() - function
operator fun Int.times(function: Function1) = asConstant() * function
operator fun Int.div(function: Function1) = asConstant() / function

operator fun Int.plus(expression: Expression) = asConstant() + expression
operator fun Int.minus(expression: Expression) = asConstant() - expression
operator fun Int.times(expression: Expression) = asConstant() * expression
operator fun Int.div(expression: Expression) = asConstant() / expression

operator fun Int.plus(function: DifferentiableFunction1) = asConstant() + function
operator fun Int.minus(function: DifferentiableFunction1) = asConstant() - function
operator fun Int.times(function: DifferentiableFunction1) = asConstant() * function
operator fun Int.div(function: DifferentiableFunction1) = asConstant() / function

operator fun Int.plus(expression: DifferentiableExpression) = asConstant() + expression
operator fun Int.minus(expression: DifferentiableExpression) = asConstant() - expression
operator fun Int.times(expression: DifferentiableExpression) = asConstant() * expression
operator fun Int.div(expression: DifferentiableExpression) = asConstant() / expression


fun affineFunction(y0: Double, m: Double) = DefaultAffineFunction(y0, m)

private typealias Point = Pair<Double, Double>
private val Point.x get() = first
private val Point.y get() = first

fun linearInterpolator(point1: Point, point2: Point) =
        affineFunction(
                y0 = point1.y,
                m = (point2.y - point1.y) / (point2.x - point1.x)
        )


fun linearFunction(m: Double): LinearFunction = DefaultLinearFunction(m)