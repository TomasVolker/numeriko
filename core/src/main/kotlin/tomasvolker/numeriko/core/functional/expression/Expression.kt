package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.function1.DifferentiableExpressionFunction1
import tomasvolker.numeriko.core.functional.function1.ExpressionFunction1

interface Expression {

    fun evaluate(vararg values: Pair<Variable, Double>): Double =
            evaluate(values.toMap())

    fun evaluate(variableValues: Map<Variable, Double>): Double

    fun variables(): Set<Variable>

    operator fun invoke(vararg values: Pair<Variable, Double>) =
            evaluate(*values)

    operator fun invoke(variableValues: Map<Variable, Double>) =
            evaluate(variableValues)

    fun toString(vararg values: Pair<Variable, String>): String =
            toString(values.toMap())

    fun toString(variableValues: Map<Variable, String>): String

}

interface DifferentiableExpression: Expression {

    fun derivative(withRespectTo: Variable): DifferentiableExpression

}

fun Expression.defaultToString() =
        toString(variables().map { it to it.name }.toMap())


fun Expression.functionOf(variable: Variable) =
        ExpressionFunction1(
                variable = variable,
                expression = this
        )

fun DifferentiableExpression.functionOf(variable: Variable) =
        DifferentiableExpressionFunction1(
                variable = variable,
                expression = this
        )