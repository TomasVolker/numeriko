package tomasvolker.simboliko.expression

import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableAssignmentContext
import tomasvolker.simboliko.expression.variable.VariableReplacementContext

class Literal<out T>(
        val value: T
): Expression<T> {

    override fun evaluate(context: VariableAssignmentContext): T =
            value

    override fun replace(context: VariableReplacementContext): Expression<T> =
            this

    override fun toString(variableValues: Map<Variable<*>, String>): String =
            value.toString()

    override fun variables(): Set<Variable<*>> = emptySet()

}