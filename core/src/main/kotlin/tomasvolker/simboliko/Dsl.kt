package tomasvolker.simboliko

import tomasvolker.simboliko.constant.*
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.Literal
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.function1.operators.*
import tomasvolker.simboliko.function2.operators.*
import tomasvolker.simboliko.number.RealNumber


fun constant(value: Double) = NumericConstant(value)
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

fun <T> variable(name: String) = Variable<T>(name)
fun <T> String.asVariableOf() = variable<T>(this)

operator fun Int.plus(function: RealFunction1) = asConstant() + function
operator fun Int.minus(function: RealFunction1) = asConstant() - function
operator fun Int.times(function: RealFunction1) = asConstant() * function
operator fun Int.div(function: RealFunction1) = asConstant() / function

operator fun Int.plus(expression: Expression<RealNumber>) = asConstant() + expression
operator fun Int.minus(expression: Expression<RealNumber>) = asConstant() - expression
operator fun Int.times(expression: Expression<RealNumber>) = asConstant() * expression
operator fun Int.div(expression: Expression<RealNumber>) = asConstant() / expression

operator fun Int.plus(function: DifferentiableFunction1) = asConstant() + function
operator fun Int.minus(function: DifferentiableFunction1) = asConstant() - function
operator fun Int.times(function: DifferentiableFunction1) = asConstant() * function
operator fun Int.div(function: DifferentiableFunction1) = asConstant() / function

operator fun Int.times(value: RealConstant) = when(value) {
    is Zero -> Zero
    is One -> value
    is IntegerConstant -> constant(this * value.integerValue)
    else -> constant(this).times(value)
}

fun <T> T.asExpression() = asLiteral()
fun <T> T.asLiteral() = Literal(this)

/*
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
*/