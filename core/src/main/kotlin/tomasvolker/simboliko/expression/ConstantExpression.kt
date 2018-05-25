package tomasvolker.simboliko.expression

import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.expression.variable.VariableContext

class ConstantExpression(
        val expression: RealExpression
): Constant {

    init {

        val variables = expression.variables()

        require(variables.isEmpty()) {
            "RealExpression depends on variables: $variables"
        }
    }

    override fun evaluate(context: VariableContext) =
            this

    override val doubleValue: Double
        get() = expression.compute()

    override fun defaultToString() =
            expression.toString(emptyMap())

    override fun toString() = defaultToString()

}