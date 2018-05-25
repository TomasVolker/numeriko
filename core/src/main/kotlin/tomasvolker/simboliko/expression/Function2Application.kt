package tomasvolker.simboliko.expression

import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableContext
import tomasvolker.simboliko.function2.RealFunction2

class Function2Application(
        val function: RealFunction2,
        val input1: RealExpression,
        val input2: RealExpression
): RealExpression {

    override fun variables() = input1.variables() + input2.variables()

    override fun dependsOn(variable: Variable<*>) =
            input1.dependsOn(variable) || input2.dependsOn(variable)

    override fun compute(variableValues: Map<Variable<*>, Double>) =
            function(input1(variableValues), input2(variableValues))

    override fun evaluate(context: VariableContext) =
            function(input1(context), input2(context))

    override fun toString(variableValues: Map<Variable<*>, String>) =
            function.toString(
                    input1.toString(variableValues),
                    input2.toString(variableValues)
            )

    override fun toString() = defaultToString()

}