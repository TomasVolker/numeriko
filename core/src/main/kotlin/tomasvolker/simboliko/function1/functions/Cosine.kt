package tomasvolker.simboliko.function1.functions

import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.expression.RealExpression
import kotlin.math.cos
import kotlin.math.sin

object Cosine: DifferentiableFunction1 {

    override fun compute(input: Double) = cos(input)

    override fun derivative() = -Sine

    override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = defaultToString()

}

fun cos(input: Constant) = Cosine(input)
fun cos(input: RealExpression) = Cosine(input)
fun cos(input: RealFunction1) = Cosine(input)
fun cos(input: DifferentiableFunction1) = Cosine(input)
