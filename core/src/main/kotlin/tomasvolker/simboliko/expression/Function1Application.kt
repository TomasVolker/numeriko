package tomasvolker.simboliko.expression

import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableAssignmentContext
import tomasvolker.simboliko.expression.variable.VariableReplacementContext
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.number.RealNumber

class Function1Application(
        val function: RealFunction1,
        val input: Expression<RealNumber>
): Expression<RealNumber> {

    override fun evaluate(context: VariableAssignmentContext) =
            function(input.evaluate(context))

    override fun replace(context: VariableReplacementContext) =
            function(input.replace(context))

    override fun variables() = input.variables()

    override fun dependsOn(variable: Variable<*>) =
            input.dependsOn(variable)

    override fun toString(variableValues: Map<Variable<*>, String>) =
            function.toString(input.toString(variableValues))

    override fun toString() = defaultToString()

}



