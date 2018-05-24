package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.DifferentiableFunction2
import tomasvolker.numeriko.core.functional.asVariable

class DifferentiableExpressionFunction2(
        variable1: Variable,
        variable2: Variable,
        override val expression: DifferentiableExpression
): ExpressionFunction2(variable1, variable2, expression),
        DifferentiableFunction2 {

    override fun derivative1(): DifferentiableFunction2 =
            DifferentiableExpressionFunction2(
                    variable1 = variable1,
                    variable2 = variable2,
                    expression = expression.derivative(variable1)
            )

    override fun derivative2(): DifferentiableFunction2 =
            DifferentiableExpressionFunction2(
                    variable1 = variable1,
                    variable2 = variable2,
                    expression = expression.derivative(variable2)
            )

}

fun differentiableFunction2(expression: (Variable, Variable)->DifferentiableExpression): DifferentiableFunction2 {
    val x1 = "x1".asVariable()
    val x2 = "x2".asVariable()
    return DifferentiableExpressionFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}