package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.function1.DifferentiableExpressionFunction1
import tomasvolker.numeriko.core.functional.function1.ExpressionFunction1
import tomasvolker.numeriko.core.functional.function1.operators.NegateFunction
import tomasvolker.numeriko.core.functional.function2.operators.Addition
import tomasvolker.numeriko.core.functional.function2.operators.Division
import tomasvolker.numeriko.core.functional.function2.operators.Multiplication
import tomasvolker.numeriko.core.functional.function2.operators.Subtraction

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

    operator fun unaryPlus() = this

    operator fun unaryMinus(): Expression =
            NegateFunction(this)

    operator fun plus(other: Expression) =
            Addition(this, other)

    operator fun minus(other: Expression) =
            Subtraction(this, other)

    operator fun times(other: Expression) =
            Multiplication(this, other)

    operator fun div(other: Expression) =
            Division(this, other)

}

interface DifferentiableExpression: Expression {

    fun derivative(withRespectTo: Variable): DifferentiableExpression

    override operator fun unaryPlus() = this

    override operator fun unaryMinus(): DifferentiableExpression =
            NegateFunction(this)

    operator fun plus(other: DifferentiableExpression) =
            Addition(this, other)

    operator fun minus(other: DifferentiableExpression) =
            Subtraction(this, other)

    operator fun times(other: DifferentiableExpression) =
            Multiplication(this, other)

    operator fun div(other: DifferentiableExpression) =
            Division(this, other)

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