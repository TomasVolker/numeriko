package tomasvolker.numeriko.core.functional.function1

import tomasvolker.numeriko.core.functional.expression.Expression
import tomasvolker.numeriko.core.functional.expression.Variable
import tomasvolker.numeriko.core.functional.variable

open class ExpressionFunction1(
        val variable: Variable,
        expression: Expression
): Function1 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.variables() - setOf(variable)

        require(undefinedVariables.isEmpty()) {
            "Expression depends on other than $variable: $undefinedVariables"
        }

    }

    open val expression: Expression
        get() =
        _expression

    override fun invoke(input: Double) =
            expression(variable to input)

    override fun toString(input: String) =
            expression.toString(variable to input)

    override fun toString() = defaultToString()

}

inline fun function1(expression: (Variable)-> Expression): Function1 {
    val x = variable("x")
    return ExpressionFunction1(
            variable = x,
            expression = expression(x)
    )
}

