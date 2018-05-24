package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.Function1
import tomasvolker.numeriko.core.functional.Function2

class Function1Application(
        val function: Function1,
        val input: Expression
): Expression {

    override fun dependsOn() = input.dependsOn()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

}

class Function2Application(
        val function: Function2,
        val input1: Expression,
        val input2: Expression
): Expression {

    override fun dependsOn() = input1.dependsOn() + input2.dependsOn()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input1(variableValues), input2(variableValues))

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(
                    input1.toString(variableValues),
                    input2.toString(variableValues)
            )

}
