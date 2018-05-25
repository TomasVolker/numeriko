package tomasvolker.simboliko.function1.functions

import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.expression.RealExpression
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
fun sin(input: RealExpression) = Sine(input)
fun sin(input: RealFunction1) = Sine(input)
fun sin(input: DifferentiableFunction1) = Sine(input)
