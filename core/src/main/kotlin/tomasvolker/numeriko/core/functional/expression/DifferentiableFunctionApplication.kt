package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.function1.*
import tomasvolker.numeriko.core.functional.function2.operators.*

class DifferentiableFunction1Application(
        val function: DifferentiableFunction1,
        val input: DifferentiableExpression
): DifferentiableExpression {

    override fun variables() = input.variables()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun derivative(withRespectTo: Variable): DifferentiableExpression =
            function.derivative()(input) * input.derivative(withRespectTo)

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}

