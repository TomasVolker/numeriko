package tomasvolker.simboliko.expression

import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableAssignmentContext
import tomasvolker.simboliko.expression.variable.VariableReplacementContext
import tomasvolker.simboliko.function2.RealFunction2
import tomasvolker.simboliko.number.RealNumber

class Function2Application(
        val function: RealFunction2,
        val input1: Expression<RealNumber>,
        val input2: Expression<RealNumber>
): Expression<RealNumber> {

    override fun variables() = input1.variables() + input2.variables()

    override fun dependsOn(variable: Variable<*>) =
            input1.dependsOn(variable) || input2.dependsOn(variable)

    fun compute(variableValues: Map<Variable<*>, Double>) =
            function(input1.compute(variableValues), input2.compute(variableValues))

    override fun evaluate(context: VariableAssignmentContext) =
            function(input1.evaluate(context), input2.evaluate(context))

    override fun replace(context: VariableReplacementContext) =
            function(input1.replace(context), input2.replace(context))

    override fun toString(variableValues: Map<Variable<*>, String>) =
            function.toString(
                    input1.toString(variableValues),
                    input2.toString(variableValues)
            )

    override fun toString() = defaultToString()

}