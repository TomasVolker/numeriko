package tomasvolker.simboliko.expression

import tomasvolker.simboliko.function1.DifferentiableFunction1

class DifferentiableFunction1Application(
        val function: DifferentiableFunction1,
        val input: DifferentiableExpression
): DifferentiableExpression {

    override fun variables() = input.variables()

    override fun compute(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun evaluate(variableValues: Map<Variable, Expression>) =
            function(input(variableValues))

    override fun derivative(withRespectTo: Variable): DifferentiableExpression =
            function.derivative()(input) * input.derivative(withRespectTo)

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}

