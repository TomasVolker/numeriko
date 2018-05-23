package tomasvolker.numeriko.core.functional.double

open class FunctionComposition<out O: Function, out I: Function>(
        val outer: O,
        val inner: I
): Function {

    override fun invoke(input: Double) = outer(inner(input))
    
    override fun toString(input: String) =
            outer.toString("(${inner.toString(input)})")

}

class DifferentiableComposition<out O: DifferentiableFunction, out I: DifferentiableFunction>(
        outer: O,
        inner: I
): FunctionComposition<O, I>(outer, inner), DifferentiableFunction {

    override fun differentiate() =
            outer.differentiate()(inner) * inner.differentiate()

    override fun derivativeAt(input: Double) =
            outer.derivativeAt(inner(input)) * inner.derivativeAt(input)
    
}


open class FunctionAddition<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) + right(input)

    override fun toString(input: String) =
            "${left.toString(input)} + ${right.toString(input)}"

}

class DifferentiableAddition<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): FunctionAddition<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() + right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) + right.derivativeAt(input)

}


open class FunctionSubtraction<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) - right(input)

    override fun toString(input: String) =
            "${left.toString(input)} - ${right.toString(input)}"

}

class DifferentiableSubtraction<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): FunctionSubtraction<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() - right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) - right.derivativeAt(input)

}


open class FunctionMultiplication<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) * right(input)

    override fun toString(input: String) =
            "(${left.toString(input)}) * (${right.toString(input)})"

}

class DifferentiableMultiplication<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): FunctionMultiplication<L, R>(left, right), DifferentiableFunction {

    override fun differentiate() =
            left.differentiate() * right + left * right.differentiate()

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) * right(input) + left(input) * right.derivativeAt(input)

}


open class FunctionDivision<out L: Function, out R: Function>(
        val left: L,
        val right: R
): Function {

    override fun invoke(input: Double) = left(input) / right(input)

    override fun toString(input: String) =
            "(${left.toString(input)}) / (${right.toString(input)})"

}

class DifferentiableDivision<out L: DifferentiableFunction, out R: DifferentiableFunction>(
        left: L,
        right: R
): FunctionDivision<L, R>(left, right), DifferentiableFunction {

    override fun differentiate(): DifferentiableFunction {
        val rightDerivative = right.differentiate()
        val leftDerivative = left.differentiate()

        return (leftDerivative * right - left * rightDerivative) / (leftDerivative * leftDerivative)
    }

    override fun derivativeAt(input: Double) =
            left.derivativeAt(input) * right(input) + left(input) * right.derivativeAt(input)

}
