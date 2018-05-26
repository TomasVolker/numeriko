package tomasvolker.simboliko.function2

import tomasvolker.simboliko.asVariableOf
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.compute
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.number.RealNumber

open class ExpressionFunction2(
        val variable1: Variable<RealNumber>,
        val variable2: Variable<RealNumber>,
        expression: Expression<RealNumber>
): RealFunction2 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.variables() - setOf(variable1, variable2)

        require(undefinedVariables.isEmpty()) {
            "RealExpression depends on other than $variable1 and $variable2: $undefinedVariables"
        }

    }

    open val expression: Expression<RealNumber> get() = _expression

    override fun compute(input1: Double, input2: Double) =
            expression.compute(
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


fun function2(expression: (x1: Variable<RealNumber>, x2: Variable<RealNumber>)-> Expression<RealNumber>): RealFunction2 {
    val x1 = "x1".asVariableOf<RealNumber>()
    val x2 = "x2".asVariableOf<RealNumber>()
    return ExpressionFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}

