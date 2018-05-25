package tomasvolker.simboliko.function1

import tomasvolker.simboliko.expression.DifferentiableExpression
import tomasvolker.simboliko.expression.Variable
import tomasvolker.simboliko.variable

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

fun differentiableFunction1(expression: (Variable)-> DifferentiableExpression): DifferentiableFunction1 {
    val x = variable("x")
    return DifferentiableExpressionFunction1(
            variable = x,
            expression = expression(x)
    )
}
