package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.constant
import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.constant.Zero
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
            simplifyPlus(other) ?:
            Addition(this, other)

    operator fun minus(other: Expression) =
            simplifyMinus(other) ?:
            Subtraction(this, other)

    operator fun times(other: Expression) =
            simplifyTimes(other) ?:
            Multiplication(this, other)

    operator fun div(other: Expression) =
            simplifyDiv(other) ?:
            Division(this, other)

    operator fun plus(other: Int) = plus(constant(other))
    operator fun minus(other: Int) = minus(constant(other))
    operator fun times(other: Int) = times(constant(other))
    operator fun div(other: Int) = div(constant(other))

    fun defaultToString() =
            toString(variables().map { it to it.name }.toMap())

    // Simplifications

    fun simplifyPlus(other: Expression) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyMinus(other: Expression) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyTimes(other: Expression) = when(other) {
        is Zero -> Zero
        is One -> this
        else -> null
    }

    fun simplifyDiv(other: Expression) = when(other) {
        is One -> this
        else -> null
    }

}

interface DifferentiableExpression: Expression {

    fun derivative(withRespectTo: Variable): DifferentiableExpression

    override operator fun unaryPlus() = this

    override operator fun unaryMinus(): DifferentiableExpression =
            NegateFunction(this)

    operator fun plus(other: DifferentiableExpression): DifferentiableExpression =
            simplifyPlus(other) as? DifferentiableExpression ?:
            Addition(this, other)

    operator fun minus(other: DifferentiableExpression): DifferentiableExpression =
            simplifyMinus(other) as? DifferentiableExpression ?:
            Subtraction(this, other)

    operator fun times(other: DifferentiableExpression): DifferentiableExpression =
            simplifyTimes(other) as? DifferentiableExpression ?:
            Multiplication(this, other)

    operator fun div(other: DifferentiableExpression): DifferentiableExpression =
            simplifyMinus(other) as? DifferentiableExpression ?:
            Division(this, other)

    override operator fun plus(other: Int) = plus(constant(other))
    override operator fun minus(other: Int) = minus(constant(other))
    override operator fun times(other: Int) = times(constant(other))
    override operator fun div(other: Int) = div(constant(other))

}


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