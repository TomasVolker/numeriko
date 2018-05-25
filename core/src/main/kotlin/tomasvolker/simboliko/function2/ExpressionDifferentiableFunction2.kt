package tomasvolker.simboliko.function2

import tomasvolker.simboliko.asVariable
import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.expression.derivative
import tomasvolker.simboliko.expression.isDifferentiable
import tomasvolker.simboliko.expression.variable.RealVariable

class ExpressionDifferentiableFunction2(
        variable1: RealVariable,
        variable2: RealVariable,
        override val expression: RealExpression
): ExpressionFunction2(variable1, variable2, expression),
        DifferentiableFunction2 {

    init {

        require(expression.isDifferentiable(variable1)) {
            "Expression $expression is not differentiables with respect to $variable1"
        }

        require(expression.isDifferentiable(variable2)) {
            "Expression $expression is not differentiables with respect to $variable2"
        }

    }

    override fun derivative1(): DifferentiableFunction2 =
            ExpressionDifferentiableFunction2(
                    variable1 = variable1,
                    variable2 = variable2,
                    expression = expression.derivative(variable1)
            )

    override fun derivative2(): DifferentiableFunction2 =
            ExpressionDifferentiableFunction2(
                    variable1 = variable1,
                    variable2 = variable2,
                    expression = expression.derivative(variable2)
            )

}

fun differentiableFunction2(expression: (RealVariable, RealVariable)-> RealExpression): DifferentiableFunction2 {
    val x1 = "x1".asVariable()
    val x2 = "x2".asVariable()
    return ExpressionDifferentiableFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}

fun RealExpression.asFunctionOf(variable1: RealVariable, variable2: RealVariable) = when {
    this.isDifferentiable(variable1, variable2) ->
        ExpressionDifferentiableFunction2(
                variable1 = variable1,
                variable2 = variable2,
                expression = this
        )
    else ->
        ExpressionFunction2(
                variable1 = variable1,
                variable2 = variable2,
                expression = this
        )
}

