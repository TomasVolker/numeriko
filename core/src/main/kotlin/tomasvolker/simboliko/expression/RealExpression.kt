package tomasvolker.simboliko.expression

import tomasvolker.simboliko.constant
import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.constant.Zero
import tomasvolker.simboliko.expression.variable.*
import tomasvolker.simboliko.function1.ExpressionDifferentiableFunction1
import tomasvolker.simboliko.function1.ExpressionFunction1
import tomasvolker.simboliko.function1.operators.*
import tomasvolker.simboliko.function2.ExpressionFunction2
import tomasvolker.simboliko.function2.operators.*
import tomasvolker.simboliko.number.*

interface Expression<out T> {

    fun evaluate(vararg values: VariableAssignment<*>): Expression<T> =
            evaluate(values.toVariableContext())

    fun evaluate(context: VariableContext): Expression<T>

    operator fun invoke(vararg values: VariableAssignment<*>) =
            evaluate(*values)

    operator fun invoke(context: VariableContext) =
            evaluate(context)

    fun toString(vararg values: Pair<Variable<*>, String>): String =
            toString(values.toMap())

    fun toString(variableValues: Map<Variable<*>, String>): String

    fun defaultToString() =
            toString(variables().map { it to it.name }.toMap())

    fun variables(): Set<Variable<*>>

    fun dependsOn(variable: Variable<*>): Boolean =
            variable in variables()

}

interface RealExpression: Expression<RealNumber> {

    fun compute(vararg values: Pair<Variable<*>, Double>): Double =
            compute(values.toMap())

    override fun evaluate(vararg values: VariableAssignment<*>): RealExpression =
            evaluate(values.toVariableContext())

    fun compute(variableValues: Map<Variable<*>, Double>): Double

    override fun evaluate(context: VariableContext): RealExpression

    override fun variables(): Set<Variable<*>>

    operator fun invoke(vararg values: Pair<Variable<*>, Double>) =
            compute(*values)

    operator fun invoke(variableValues: Map<Variable<*>, Double>) =
            compute(variableValues)

    override operator fun invoke(vararg values: VariableAssignment<*>): RealExpression =
            evaluate(*values)

    override operator fun invoke(context: VariableContext) =
            evaluate(context)

    override fun toString(vararg values: Pair<Variable<*>, String>): String =
            toString(values.toMap())

    override fun toString(variableValues: Map<Variable<*>, String>): String

    operator fun unaryPlus() = this

    operator fun unaryMinus(): RealExpression =
            NegateFunction(this)

    operator fun plus(other: RealExpression) =
            simplifyPlus(other) ?:
            Addition(this, other)

    operator fun minus(other: RealExpression) =
            simplifyMinus(other) ?:
            Subtraction(this, other)

    operator fun times(other: RealExpression) =
            simplifyTimes(other) ?:
            Multiplication(this, other)

    operator fun div(other: RealExpression) =
            simplifyDiv(other) ?:
            Division(this, other)

    operator fun plus(other: Int) = plus(constant(other))
    operator fun minus(other: Int) = minus(constant(other))
    operator fun times(other: Int) = times(constant(other))
    operator fun div(other: Int) = div(constant(other))

    // Simplifications

    fun simplifyPlus(other: RealExpression) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyMinus(other: RealExpression) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyTimes(other: RealExpression) = when(other) {
        is Zero -> Zero
        is One -> this
        else -> null
    }

    fun simplifyDiv(other: RealExpression) = when(other) {
        is One -> this
        else -> null
    }

}


fun RealExpression.asFunctionOf(variable: RealVariable) = when {
    this.isDifferentiable(variable) ->
        ExpressionDifferentiableFunction1(
                variable = variable,
                expression = this
        )
    else -> ExpressionFunction1(
            variable = variable,
            expression = this
    )
}
