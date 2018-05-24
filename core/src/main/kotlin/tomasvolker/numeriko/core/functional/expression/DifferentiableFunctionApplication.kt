package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.DifferentiableFunction1
import tomasvolker.numeriko.core.functional.DifferentiableFunction2
import tomasvolker.numeriko.core.functional.invoke
import tomasvolker.numeriko.core.functional.operators.plus
import tomasvolker.numeriko.core.functional.operators.times

class DifferentiableFunction1Application(
        val function: DifferentiableFunction1,
        val input: DifferentiableExpression
): DifferentiableExpression {

    override fun dependsOn() = input.dependsOn()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun derivative(withRespectTo: Variable) =
            function.derivative()(input) * input.derivative(withRespectTo)

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}

class DifferentiableFunction2Application(
        val function: DifferentiableFunction2,
        val input1: DifferentiableExpression,
        val input2: DifferentiableExpression
): DifferentiableExpression {

    override fun dependsOn() = input1.dependsOn() + input2.dependsOn()

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
