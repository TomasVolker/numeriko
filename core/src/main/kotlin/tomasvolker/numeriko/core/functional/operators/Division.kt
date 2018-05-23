package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.DifferentiableFunction
import tomasvolker.numeriko.core.functional.Function


open class Division<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) / right(input)

    override fun toString(input: String) =
            "${left.toString(input)} / ${right.toString(input)}"

    override fun toString() = toString("x")

}

class DifferentiableDivision<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): Division<L, R>(left, right), DifferentiableFunction {

    override fun differentiate(): DifferentiableFunction {
        val rightDerivative = right.differentiate()
        val leftDerivative = left.differentiate()

        return (leftDerivative * right - left * rightDerivative) / (leftDerivative * leftDerivative)
    }

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) * right(input) + left(input) * right.derivativeAt(input)

}
