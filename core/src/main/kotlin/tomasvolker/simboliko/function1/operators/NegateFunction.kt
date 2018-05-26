package tomasvolker.simboliko.function1.operators

import tomasvolker.simboliko.constant.MinusOne
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.number.RealNumber

object NegateFunction: DifferentiableFunction1 {

    override fun compute(input: Double) = -input

    override fun derivative() = MinusOne
    override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = "(-${input})"

}

operator fun Expression<RealNumber>.unaryMinus() = NegateFunction(this)