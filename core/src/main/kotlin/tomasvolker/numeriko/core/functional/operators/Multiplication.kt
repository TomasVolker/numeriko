package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.DifferentiableFunction
import tomasvolker.numeriko.core.functional.Function

open class Multiplication<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) * right(input)

    override fun toString(input: String) =
            "${left.toString(input)} * ${right.toString(input)}"

    override fun toString() = toString("x")

}

class DifferentiableMultiplication<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): Multiplication<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() * right + left * right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) * right(input) + left(input) * right.derivativeAt(input)

}