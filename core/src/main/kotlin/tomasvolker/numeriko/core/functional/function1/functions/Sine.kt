package tomasvolker.numeriko.core.functional.function1.functions

import tomasvolker.numeriko.core.functional.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.function1.Function1
import tomasvolker.numeriko.core.functional.function1.defaultToString
import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Expression
import tomasvolker.numeriko.core.functional.function1.invoke
import kotlin.math.sin

object Sine: DifferentiableFunction1 {

    override fun invoke(input: Double) = sin(input)

    override fun derivative() = Cosine

    //override fun derivativeAt(input: Double) = cos(input)

    override fun toString(input: String) = "sin(${input})"

    override fun toString() = defaultToString()

}

fun sin(input: Expression) = Sine(input)
fun sin(input: DifferentiableExpression) = Sine(input)
fun sin(input: Function1) = Sine(input)
fun sin(input: DifferentiableFunction1) = Sine(input)
