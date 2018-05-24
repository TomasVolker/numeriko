package tomasvolker.numeriko.core.functional.function1.functions

import tomasvolker.numeriko.core.functional.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.function1.Function1
import tomasvolker.numeriko.core.functional.function1.defaultToString
import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Expression
import tomasvolker.numeriko.core.functional.function1.invoke
import tomasvolker.numeriko.core.functional.function1.operators.unaryMinus
import kotlin.math.cos

object Cosine: DifferentiableFunction1 {

    override fun invoke(input: Double) = cos(input)

    override fun derivative() = -Sine

    //override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = defaultToString()

}

fun cos(input: Expression) = Cosine(input)
fun cos(input: DifferentiableExpression) = Cosine(input)
fun cos(input: Function1) = Cosine(input)
fun cos(input: DifferentiableFunction1) = Cosine(input)
