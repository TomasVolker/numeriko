package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.operators.unaryMinus
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

object Exponential: DifferentiableFunction1 {

    override fun invoke(input: Double) = exp(input)

    override fun derivative() = Exponential

    //override fun derivativeAt(input: Double) = exp(input)

    override fun toString(input: String) = "exp(${input})"

    override fun toString() = defaultToString()

}

object Cosine: DifferentiableFunction1 {

    override fun invoke(input: Double) = cos(input)

    override fun derivative() = -Sine

    //override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = defaultToString()

}

object Sine: DifferentiableFunction1 {

    override fun invoke(input: Double) = sin(input)

    override fun derivative() = Cosine

    //override fun derivativeAt(input: Double) = cos(input)

    override fun toString(input: String) = "sin(${input})"

    override fun toString() = defaultToString()

}
