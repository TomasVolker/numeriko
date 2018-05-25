package tomasvolker.simboliko.expression.variable

import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.number.RealNumber

class RealVariable(
        override val name: String
): Variable<RealNumber>, RealExpression {

    override fun compute(variableValues: Map<Variable<*>, Double>) =
            variableValues.getOrElse(this) {
                throw NoVariableAssignmentException(this)
            }

    override fun evaluate(context: VariableContext) =
            context[this] as RealExpression

    override fun variables() = setOf(this)

    override fun toString(variableValues: Map<Variable<*>, String>) =
            variableValues.getOrElse(this) {
                throw NoVariableAssignmentException(this)
            }

    override fun toString() = name

    override fun equals(other: Any?) =
            this === other

    override fun hashCode() = name.hashCode()

}
