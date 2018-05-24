package tomasvolker.numeriko.core.simbolic.function1.functions

import tomasvolker.numeriko.core.simbolic.constant.Constant
import tomasvolker.numeriko.core.simbolic.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.simbolic.function1.Function1
import tomasvolker.numeriko.core.simbolic.expression.DifferentiableExpression
import tomasvolker.numeriko.core.simbolic.expression.Expression
import kotlin.math.cos
import kotlin.math.sin

object Sine: DifferentiableFunction1 {

    override fun compute(input: Double) = sin(input)

    override fun derivative() = Cosine

    override fun derivativeAt(input: Double) = cos(input)

    override fun toString(input: String) = "sin(${input})"

    override fun toString() = defaultToString()

}

fun sin(input: Constant) = Sine(input)
fun sin(input: Expression) = Sine(input)
fun sin(input: DifferentiableExpression) = Sine(input)
fun sin(input: Function1) = Sine(input)
fun sin(input: DifferentiableFunction1) = Sine(input)
