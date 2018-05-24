package tomasvolker.numeriko.core.simbolic.expression

import tomasvolker.numeriko.core.simbolic.function1.Function1

class Function1Application(
        val function: Function1,
        val input: Expression
): Expression {

    override fun variables() = input.variables()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

}



