package tomasvolker.simboliko.function2

import tomasvolker.simboliko.asVariableOf
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.derivative
import tomasvolker.simboliko.expression.isDifferentiable
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.number.RealNumber

class ExpressionDifferentiableFunction2(
        variable1: Variable<RealNumber>,
        variable2: Variable<RealNumber>,
        override val expression: Expression<RealNumber>
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

fun differentiableFunction2(expression: (Variable<RealNumber>, Variable<RealNumber>)-> Expression<RealNumber>): DifferentiableFunction2 {
    val x1 = "x1".asVariableOf<RealNumber>()
    val x2 = "x2".asVariableOf<RealNumber>()
    return ExpressionDifferentiableFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}

fun Expression<RealNumber>.asFunctionOf(variable1: Variable<RealNumber>, variable2: Variable<RealNumber>) = when {
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

