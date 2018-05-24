package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.Function1
import tomasvolker.numeriko.core.functional.defaultToString

open class ExpressionFunction1(
        val variable: Variable,
        expression: Expression
): Function1 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.dependsOn() - setOf(variable)

        require(undefinedVariables.isEmpty()) {
            "Expression depends on other than $variable: $undefinedVariables"
        }

    }

    open val expression: Expression get() =
        _expression

    override fun invoke(input: Double) =
            expression(variable to input)

    override fun toString(input: String) =
            expression.toString(variable to input)

    override fun toString() = defaultToString()

}

val X = Variable("x")

fun function1(expression: (Variable)->Expression): Function1 =
        ExpressionFunction1(
                variable = X,
                expression = expression(X)
        )
