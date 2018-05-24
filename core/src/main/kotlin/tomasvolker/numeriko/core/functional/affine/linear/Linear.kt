package tomasvolker.numeriko.core.functional.affine.linear

import tomasvolker.numeriko.core.functional.affine.AffineFunction
import tomasvolker.numeriko.core.functional.asConstant

interface LinearFunction: AffineFunction {

    override val y0 get() = 0.0

    override fun invoke(input: Double) = m * input
    override fun derivative() = m.asConstant()

}

class DefaultLinearFunction(
        override val m  : Double
): LinearFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$m * $input"

}

