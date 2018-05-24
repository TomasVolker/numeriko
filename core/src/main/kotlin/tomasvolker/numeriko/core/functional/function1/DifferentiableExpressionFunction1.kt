package tomasvolker.numeriko.core.functional.function1

import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Variable
import tomasvolker.numeriko.core.functional.variable

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
