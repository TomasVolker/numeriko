package tomasvolker.simboliko.expression

import tomasvolker.simboliko.function1.Function1

class Function1Application(
        val function: Function1,
        val input: Expression
): Expression {

    override fun variables() = input.variables()

    override fun compute(variableValues: Map<Variable, Double>) =
            function(input(variableValues))

    override fun evaluate(variableValues: Map<Variable, Expression>) =
            function(input(variableValues))

    override fun toString(variableValues: Map<Variable, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}



