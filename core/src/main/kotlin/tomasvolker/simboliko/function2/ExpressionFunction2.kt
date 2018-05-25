package tomasvolker.simboliko.function2

import tomasvolker.simboliko.asVariable
import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.expression.variable.RealVariable

open class ExpressionFunction2(
        val variable1: RealVariable,
        val variable2: RealVariable,
        expression: RealExpression
): RealFunction2 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.variables() - setOf(variable1, variable2)

        require(undefinedVariables.isEmpty()) {
            "RealExpression depends on other than $variable1 and $variable2: $undefinedVariables"
        }

    }

    open val expression: RealExpression get() = _expression

    override fun compute(input1: Double, input2: Double) =
            expression(
                    variable1 to input1,
                    variable2 to input2
            )

    override fun toString(input1: String, input2: String) =
            expression.toString(
                    variable1 to input1,
                    variable2 to input2
            )

    override fun toString() = defaultToString()

}


fun function2(expression: (x1: RealVariable, x2: RealVariable)-> RealExpression): RealFunction2 {
    val x1 = "x1".asVariable()
    val x2 = "x2".asVariable()
    return ExpressionFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}

