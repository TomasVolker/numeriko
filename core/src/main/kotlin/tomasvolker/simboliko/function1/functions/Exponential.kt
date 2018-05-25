package tomasvolker.simboliko.function1.functions

import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.constant.E
import tomasvolker.simboliko.constant.Zero
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.Function1
import tomasvolker.simboliko.expression.DifferentiableExpression
import tomasvolker.simboliko.expression.Expression
import kotlin.math.exp

object Exponential: DifferentiableFunction1 {

    override fun compute(input: Double) = exp(input)

    override fun derivative() = Exponential

    override fun derivativeAt(input: Double) = exp(input)

    override fun toString(input: String) = "exp(${input})"

    override fun toString() = defaultToString()

}

fun exp(input: Constant) = when(input) {
    is Zero -> E
    else -> Exponential(input)
}
fun exp(input: Expression) = Exponential(input)
fun exp(input: DifferentiableExpression) = Exponential(input)
fun exp(input: Function1) = Exponential(input)
fun exp(input: DifferentiableFunction1) = Exponential(input)
