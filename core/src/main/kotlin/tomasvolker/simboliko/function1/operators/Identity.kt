package tomasvolker.simboliko.function1.operators

import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.number.RealNumber

object Identity: DifferentiableFunction1 {

    override fun compute(input: Double) = input

    override fun unaryMinus() = NegateFunction

    override fun derivative() = One
    override fun derivativeAt(input: Double) = 1.0

    override fun toString(input: String) = input

}

operator fun Expression<RealNumber>.unaryPlus() = Identity(this)