package tomasvolker.simboliko

import tomasvolker.simboliko.affine.DefaultAffineFunction
import tomasvolker.simboliko.affine.DefaultLinearFunction
import tomasvolker.simboliko.affine.LinearFunction
import tomasvolker.simboliko.constant.*
import tomasvolker.simboliko.expression.DifferentiableExpression
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.Variable
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.Function1


fun constant(value: Double): Constant = NumericConstant(value)
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

operator fun Int.times(value: Constant) = when(value) {
    is Zero -> Zero
    is One -> value
    is IntegerConstant -> constant(this * value.integerValue)
    else -> constant(this).times(value)
}


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