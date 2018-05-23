package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.Function

open class Addition<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) + right(input)

    override fun toString(input: String) =
            "(${left.toString(input)} + ${right.toString(input)})"

    override fun toString() = toString("x")

}

class DifferentiableAddition<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): Addition<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() + right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) + right.derivativeAt(input)

}

fun add(left: Function, right: Function): Function {
    TODO()
}