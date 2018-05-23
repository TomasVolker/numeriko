package tomasvolker.numeriko.core.functional

import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

object Exponential: DifferentiableFunction {

    override fun invoke(input: Double) = exp(input)

    override fun differentiate() = Exponential

    override fun derivativeAt(input: Double) = exp(input)

    override fun toString(input: String) = "exp(${input})"

    override fun toString() = toString("x")

}

object Cosine: DifferentiableFunction {

    override fun invoke(input: Double) = cos(input)

    override fun differentiate() = -Sine

    override fun derivativeAt(input: Double) = -sin(input)

    override fun toString(input: String) = "cos(${input})"

    override fun toString() = toString("x")

}

object Sine: DifferentiableFunction {

    override fun invoke(input: Double) = sin(input)

    override fun differentiate() = Cosine

    override fun derivativeAt(input: Double) = cos(input)

    override fun toString(input: String) = "sin(${input})"

    override fun toString() = toString("x")

}
