package tomasvolker.numeriko.core.functional.affine

import tomasvolker.numeriko.core.functional.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.constant
import tomasvolker.numeriko.core.functional.constant.Constant

interface AffineFunction: DifferentiableFunction1 {

    val y0 : Double
    val m  : Double

    override operator fun invoke(input: Double) = y0 + m * input
    /*
    operator fun invoke(input: AffineFunction): AffineFunction =
            affineFunction(
                    y0 = this.y0 + m * input.y0,
                    m = this.m * input.m
            )
*/
    override fun derivative(): Constant =
            constant(m)

    //override fun derivativeAt(input: Double) = m

}

class DefaultAffineFunction(
        override val y0 : Double,
        override val m  : Double
): AffineFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$y0 + $m * $input"

}
