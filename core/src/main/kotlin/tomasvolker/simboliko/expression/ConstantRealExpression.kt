package tomasvolker.simboliko.expression

import tomasvolker.simboliko.constant.RealConstant
import tomasvolker.simboliko.expression.variable.VariableAssignmentContext
import tomasvolker.simboliko.expression.variable.VariableReplacementContext
import tomasvolker.simboliko.number.RealNumber

class ConstantRealExpression(
        val expression: Expression<RealNumber>
): RealConstant {

    init {

        val variables = expression.variables()

        require(variables.isEmpty()) {
            "RealExpression depends on variables: $variables"
        }
    }

    override fun replace(context: VariableReplacementContext) = this

    override fun evaluate(context: VariableAssignmentContext) = this

    override fun getDouble() = expression.compute()

    override fun defaultToString() =
            expression.toString(emptyMap())

    override fun toString() = defaultToString()

}