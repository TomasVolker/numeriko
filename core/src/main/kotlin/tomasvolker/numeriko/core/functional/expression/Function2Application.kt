package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.function2.Function2

class Function2Application(
        val function: Function2,
        val input1: Expression,
        val input2: Expression
): Expression {

    override fun variables() = input1.variables() + input2.variables()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input1(variableValues), input2(variableValues))

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(
                    input1.toString(variableValues),
                    input2.toString(variableValues)
            )

}