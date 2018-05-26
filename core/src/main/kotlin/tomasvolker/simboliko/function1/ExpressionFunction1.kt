package tomasvolker.simboliko.function1

import tomasvolker.simboliko.constant.RealConstant
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.compute
import tomasvolker.simboliko.expression.isDifferentiable
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.assignTo
import tomasvolker.simboliko.number.RealNumber
import tomasvolker.simboliko.variable

open class ExpressionFunction1(
        val variable: Variable<RealNumber>,
        expression: Expression<RealNumber>
): RealFunction1 {

    override fun invoke(input: RealNumber) =
            expression.evaluate(variable assignTo input)

    private val _expression = expression

    init {

        val undefinedVariables = expression.variables() - setOf(variable)

        require(undefinedVariables.isEmpty()) {
            "RealExpression depends on other than $variable: $undefinedVariables"
        }

    }

    open val expression: Expression<RealNumber>
        get() =
        _expression

    fun invoke(input: Variable<RealNumber>) =
            expression(variable assignTo  input)
/*
    override fun simplifyInvoke(input: RealFunction1) =
            expression(variable assignTo input(variable)).asFunctionOf(variable)
*/
    override fun compute(input: Double) =
            expression.compute(variable to input)

    override fun toString(input: String) =
            expression.toString(variable to input)

    override fun toString() = defaultToString()

}

inline fun function1(lambda: (Variable<RealNumber>)-> Expression<RealNumber>): RealFunction1 {

    val x = variable<RealNumber>("x")
    val expression = lambda(x)

    return when {
        expression is RealConstant -> expression
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


