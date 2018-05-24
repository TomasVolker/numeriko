package tomasvolker.numeriko.core.simbolic.expression

import tomasvolker.numeriko.core.simbolic.constant.Constant

class ConstantExpression(
        val expression: Expression
): Constant {

    init {

        val variables = expression.variables()

        require(variables.isEmpty()) {
            "Expression depends on variables: $variables"
        }
    }

    override fun evaluate(variableValues: Map<Variable, Expression>) =
            this

    override val doubleValue: Double
        get() = expression.compute()

    override fun defaultToString() =
            expression.toString(emptyMap())

    override fun toString() = defaultToString()

}