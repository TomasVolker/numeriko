package tomasvolker.simboliko.function1

import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.expression.derivative
import tomasvolker.simboliko.expression.variable.RealVariable
import tomasvolker.simboliko.variable

class ExpressionDifferentiableFunction1(
        variable: RealVariable,
        override val expression: RealExpression
): ExpressionFunction1(variable, expression),
        DifferentiableFunction1 {

    override fun derivative(): DifferentiableFunction1 =
            ExpressionDifferentiableFunction1(
                    variable = variable,
                    expression = expression.derivative(variable)
            )

}

fun differentiableFunction1(expression: (RealVariable)-> RealExpression): DifferentiableFunction1 {
    val x = variable("x")
    return ExpressionDifferentiableFunction1(
            variable = x,
            expression = expression(x)
    )
}
