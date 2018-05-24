package tomasvolker.numeriko.core.simbolic.function1.functions

import tomasvolker.numeriko.core.simbolic.constant.Constant
import tomasvolker.numeriko.core.simbolic.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.simbolic.function1.Function1
import tomasvolker.numeriko.core.simbolic.expression.DifferentiableExpression
import tomasvolker.numeriko.core.simbolic.expression.Expression
import kotlin.math.cos
import kotlin.math.sin

object Cosine: DifferentiableFunction1 {

    override fun invoke(input: Double) = cos(input)

    override fun derivative() = -Sine

    override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = defaultToString()

}

fun cos(input: Constant) = Cosine(input)
fun cos(input: Expression) = Cosine(input)
fun cos(input: DifferentiableExpression) = Cosine(input)
fun cos(input: Function1) = Cosine(input)
fun cos(input: DifferentiableFunction1) = Cosine(input)
