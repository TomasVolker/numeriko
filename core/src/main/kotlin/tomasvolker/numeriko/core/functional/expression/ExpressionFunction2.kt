package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.Function2
import tomasvolker.numeriko.core.functional.asVariable
import tomasvolker.numeriko.core.functional.defaultToString

open class ExpressionFunction2(
        val variable1: Variable,
        val variable2: Variable,
        expression: Expression
): Function2 {

    private val _expression = expression

    init {

        val undefinedVariables = expression.dependsOn() - setOf(variable1, variable2)

        require(undefinedVariables.isEmpty()) {
            "Expression depends on other than $variable1 and $variable2: $undefinedVariables"
        }

    }

    open val expression: Expression get() = _expression

    override fun invoke(input1: Double, input2: Double) =
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


fun function2(expression: (x1: Variable, x2: Variable)->Expression): Function2 {
    val x1 = "x1".asVariable()
    val x2 = "x2".asVariable()
    return ExpressionFunction2(
            variable1 = x1,
            variable2 = x2,
            expression = expression(x1, x2)
    )
}

