package tomasvolker.numeriko.core.functional.affine.linear

import tomasvolker.numeriko.core.functional.affine.AffineFunction
import tomasvolker.numeriko.core.functional.asConstant
import tomasvolker.numeriko.core.functional.linearFunction

interface LinearFunction: AffineFunction {

    override val y0 get() = 0.0

    override operator fun invoke(input: AffineFunction) = when(input) {
        is ConstantFunction -> invoke(input)
        is LinearFunction -> invoke(this)
        else -> super.invoke(input)
    }

    operator fun invoke(input: LinearFunction): LinearFunction =
            linearFunction(this.m * input.m)

    override fun invoke(input: Double) = m * input
    override fun differentiate() = m.asConstant()

}

class DefaultLinearFunction(
        override val m  : Double
): LinearFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$m * $input"

}

object IdentityFunction: LinearFunction {

    override val m: Double get() = 1.0

    override fun invoke(input: Double) = input

    override fun differentiate() = OneFunction
    override fun derivativeAt(input: Double) = 1.0

    override fun toString(input: String) = input

}

object NegateFunction: LinearFunction {

    override val m: Double get() = -1.0

    override fun invoke(input: Double) = -input

    override fun differentiate() = MinusOneFunction
    override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = "(-${input})"

}