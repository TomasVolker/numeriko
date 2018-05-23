package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.Function
import tomasvolker.numeriko.core.functional.affine.linear.ConstantFunction
import tomasvolker.numeriko.core.functional.affine.linear.ZeroFunction

open class Composition<out O: Function, out I: Function>(
        val outer: O,
        val inner: I
): Function {

    override fun invoke(input: Double) = outer(inner(input))

    override fun toString(input: String) =
            outer.toString("(${inner.toString(input)})")

    override fun toString() = toString("x")

}

class DifferentiableComposition<out O: DifferentiableFunction, out I: DifferentiableFunction>(
        outer: O,
        inner: I
): Composition<O, I>(outer, inner), DifferentiableFunction {

    override fun differentiate() =
            outer.differentiate()(inner) * inner.differentiate()

    override fun derivativeAt(input: Double) =
            outer.derivativeAt(inner(input)) * inner.derivativeAt(input)

}

fun compose(outer: Function, inner: Function): Function {

    return when(outer) {
        is ZeroFunction -> ZeroFunction
        is ConstantFunction -> return outer
        else -> Composition(outer, inner)
    }

}