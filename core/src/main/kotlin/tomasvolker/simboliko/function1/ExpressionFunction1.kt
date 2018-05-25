package tomasvolker.simboliko.function1

import tomasvolker.numeriko.core.functions.exp
import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.expression.variable.RealVariable
import tomasvolker.simboliko.expression.asFunctionOf
import tomasvolker.simboliko.expression.isDifferentiable
import tomasvolker.simboliko.expression.variable.assignTo
import tomasvolker.simboliko.variable

open class ExpressionFunction1(
        val variable: RealVariable,
        expression: RealExpression
): RealFunction1 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.variables() - setOf(variable)

        require(undefinedVariables.isEmpty()) {
            "RealExpression depends on other than $variable: $undefinedVariables"
        }

    }

    open val expression: RealExpression
        get() =
        _expression

    override fun invoke(input: RealExpression) =
            expression(variable assignTo  input)

    override fun simplifyInvoke(input: RealFunction1) =
            expression(variable assignTo input(variable)).asFunctionOf(variable)

    override fun compute(input: Double) =
            expression(variable to input)

    override fun toString(input: String) =
            expression.toString(variable to input)

    override fun toString() = defaultToString()

}

inline fun function1(lambda: (RealVariable)-> RealExpression): RealFunction1 {

    val x = variable("x")
    val expression = lambda(x)

    return when {
        expression is Constant -> expression
        expression.isDifferentiable(x) ->
                ExpressionDifferentiableFunction1(
                        variable = x,
                        expression = expression
                )
        else -> ExpressionFunction1(
                variable = x,
                expression = lambda(x)
        )
    }
}


