package tomasvolker.simboliko.function1

import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.derivative
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.number.RealNumber
import tomasvolker.simboliko.variable

class ExpressionDifferentiableFunction1(
        variable: Variable<RealNumber>,
        override val expression: Expression<RealNumber>
): ExpressionFunction1(variable, expression),
        DifferentiableFunction1 {

    override fun derivative(): DifferentiableFunction1 =
            ExpressionDifferentiableFunction1(
                    variable = variable,
                    expression = expression.derivative(variable)
            )

}

fun differentiableFunction1(expression: (Variable<RealNumber>)-> Expression<RealNumber>): DifferentiableFunction1 {
    val x = variable<RealNumber>("x")
    return ExpressionDifferentiableFunction1(
            variable = x,
            expression = expression(x)
    )
}
