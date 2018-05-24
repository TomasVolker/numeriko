package tomasvolker.numeriko.core.functional.function1.functions

import tomasvolker.numeriko.core.functional.constant.Constant
import tomasvolker.numeriko.core.functional.constant.E
import tomasvolker.numeriko.core.functional.constant.Zero
import tomasvolker.numeriko.core.functional.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.function1.Function1
import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Expression
import kotlin.math.exp

object Exponential: DifferentiableFunction1 {

    override fun invoke(input: Double) = exp(input)

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
