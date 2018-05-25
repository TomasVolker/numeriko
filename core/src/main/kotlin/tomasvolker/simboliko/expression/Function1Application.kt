package tomasvolker.simboliko.expression

import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableContext
import tomasvolker.simboliko.function1.RealFunction1

class Function1Application(
        val function: RealFunction1,
        val input: RealExpression
): RealExpression {

    override fun variables() = input.variables()

    override fun dependsOn(variable: Variable<*>) =
            input.dependsOn(variable)

    override fun compute(variableValues: Map<Variable<*>, Double>) =
            function(input(variableValues))

    override fun evaluate(context: VariableContext) =
            function(input.invoke(context))

    override fun toString(variableValues: Map<Variable<*>, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}



