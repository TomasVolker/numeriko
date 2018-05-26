package tomasvolker.simboliko.function1.functions

import tomasvolker.simboliko.asLiteral
import tomasvolker.simboliko.constant.*
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.number.RealNumber
import kotlin.math.cos
import kotlin.math.sin

object Cosine: DifferentiableFunction1 {
/*
    override fun invoke(input: RealNumber): RealNumber = when(input) {
        is Zero -> One
        is Pi -> MinusOne
        else -> cos(input.asLiteral())
    }
*/
    override fun compute(input: Double) = cos(input)

    override fun derivative() = -Sine

    override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = defaultToString()

}

fun cos(input: RealNumber) = Cosine(input)
fun cos(input: Expression<RealNumber>) = Cosine(input)
fun cos(input: RealFunction1) = Cosine(input)
fun cos(input: DifferentiableFunction1) = Cosine(input)
