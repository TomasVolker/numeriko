package tomasvolker.numeriko.core.simbolic.expression

import tomasvolker.numeriko.core.simbolic.function2.DifferentiableFunction2

class DifferentiableFunction2Application(
        val function: DifferentiableFunction2,
        val input1: DifferentiableExpression,
        val input2: DifferentiableExpression
): DifferentiableExpression {

    override fun variables() = input1.variables() + input2.variables()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input1(variableValues), input2(variableValues))

    override fun derivative(withRespectTo: Variable) =
            function.derivative1()(input1, input2) * input1.derivative(withRespectTo) +
            function.derivative2()(input1, input2) * input2.derivative(withRespectTo)


    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(
                    input1.toString(variableValues),
                    input2.toString(variableValues)
            )

    override fun toString() = defaultToString()

}