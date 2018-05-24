package tomasvolker.numeriko.core.simbolic.function1

import tomasvolker.numeriko.core.simbolic.constant.Constant
import tomasvolker.numeriko.core.simbolic.expression.Expression
import tomasvolker.numeriko.core.simbolic.expression.Variable
import tomasvolker.numeriko.core.simbolic.variable

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

    override fun compute(input: Double) =
            expression(variable to input)

    override fun toString(input: String) =
            expression.toString(variable to input)

    override fun toString() = defaultToString()

}

inline fun function1(lambda: (Variable)-> Expression): Function1 {

    val x = variable("x")
    val expression = lambda(x)

    return when(expression) {
        is Constant -> expression
        else -> ExpressionFunction1(
                variable = x,
                expression = lambda(x)
        )
    }
}


