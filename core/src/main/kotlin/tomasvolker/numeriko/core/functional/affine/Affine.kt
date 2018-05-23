package tomasvolker.numeriko.core.functional.affine

import tomasvolker.numeriko.core.functional.DifferentiableFunction
import tomasvolker.numeriko.core.functional.affineFunction
import tomasvolker.numeriko.core.functional.constant
import tomasvolker.numeriko.core.functional.affine.linear.ConstantFunction
import tomasvolker.numeriko.core.functional.operators.DifferentiableComposition

interface AffineFunction: DifferentiableFunction {

    val y0 : Double
    val m  : Double

    override operator fun invoke(input: Double) = y0 + m * input

    override operator fun invoke(input: DifferentiableFunction) = when(input) {
        is ConstantFunction -> invoke(input)
        is AffineFunction -> invoke(this)
        else -> DifferentiableComposition(this, input)
    }

    operator fun invoke(input: AffineFunction): AffineFunction =
            affineFunction(
                    y0 = this.y0 + m * input.y0,
                    m = this.m * input.m
            )

    override fun differentiate(): ConstantFunction =
            constant(m)

    override fun derivativeAt(input: Double) = m

}

class DefaultAffineFunction(
        override val y0 : Double,
        override val m  : Double
): AffineFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$y0 + $m * $input"

}
