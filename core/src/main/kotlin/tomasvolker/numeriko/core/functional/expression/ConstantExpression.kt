package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.constant.Constant

class ConstantExpression(
        val expression: Expression
): Constant {

    init {

        val variables = expression.variables()

        require(variables.isEmpty()) {
            "Expression depends on variables: $variables"
        }
    }

    override val doubleValue: Double
        get() = expression.evaluate()

    override fun defaultToString() =
            expression.toString(emptyMap())

    override fun toString() = defaultToString()

}