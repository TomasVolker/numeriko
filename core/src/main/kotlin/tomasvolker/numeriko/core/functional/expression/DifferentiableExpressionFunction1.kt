package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.DifferentiableFunction1

class DifferentiableExpressionFunction1(
        variable: Variable,
        override val expression: DifferentiableExpression
): ExpressionFunction1(variable, expression),
        DifferentiableFunction1 {

    override fun derivative(): DifferentiableFunction1 =
            DifferentiableExpressionFunction1(
                    variable = variable,
                    expression = expression.derivative(variable)
            )

}

fun differentiableFunction1(expression: (Variable)->DifferentiableExpression): DifferentiableFunction1 =
        DifferentiableExpressionFunction1(
                variable = X,
                expression = expression(X)
        )