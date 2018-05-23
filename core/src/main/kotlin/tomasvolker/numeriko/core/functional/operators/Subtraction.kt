package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.DifferentiableFunction
import tomasvolker.numeriko.core.functional.Function

open class Subtraction<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) - right(input)

    override fun toString(input: String) =
            "(${left.toString(input)} - ${right.toString(input)})"

    override fun toString() = toString("x")

}

class DifferentiableSubtraction<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): Subtraction<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() - right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) - right.derivativeAt(input)

}
