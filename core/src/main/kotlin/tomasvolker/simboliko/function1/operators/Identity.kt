package tomasvolker.simboliko.function1.operators

import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.affine.LinearFunction

object Identity: LinearFunction {

    override val m: Double get() = 1.0

    override fun compute(input: Double) = input

    override fun unaryMinus() = NegateFunction

    override fun derivative() = One
    override fun derivativeAt(input: Double) = 1.0

    override fun toString(input: String) = input

}

