package tomasvolker.numeriko.core.functional.function1.operators

import tomasvolker.numeriko.core.functional.affine.LinearFunction
import tomasvolker.numeriko.core.functional.constant.MinusOne

object NegateFunction: LinearFunction {

    override val m: Double get() = -1.0

    override fun invoke(input: Double) = -input

    override fun derivative() = MinusOne
    //override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = "(-${input})"

}
