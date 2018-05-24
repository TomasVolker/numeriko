package tomasvolker.numeriko.core.simbolic.function1.operators

import tomasvolker.numeriko.core.simbolic.affine.LinearFunction
import tomasvolker.numeriko.core.simbolic.constant.MinusOne

object NegateFunction: LinearFunction {

    override val m: Double get() = -1.0

    override fun compute(input: Double) = -input

    override fun unaryMinus() = Identity

    override fun derivative() = MinusOne
    override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = "(-${input})"

}
