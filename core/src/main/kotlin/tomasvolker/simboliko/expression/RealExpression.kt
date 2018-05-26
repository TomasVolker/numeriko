package tomasvolker.simboliko.expression

import tomasvolker.simboliko.asConstant
import tomasvolker.simboliko.expression.variable.*
import tomasvolker.simboliko.function1.ExpressionDifferentiableFunction1
import tomasvolker.simboliko.function1.ExpressionFunction1
import tomasvolker.simboliko.number.*


interface Expression<out T> {

    operator fun invoke(vararg values: VariableAssignment<*>) =
            evaluate(*values)

    operator fun invoke(context: VariableAssignmentContext): T =
            evaluate(context)

    fun evaluate(vararg values: VariableAssignment<*>) =
            evaluate(values.toVariableContext())

    fun evaluate(context: VariableAssignmentContext): T

    fun replace(vararg values: VariableReplacement<*>) =
            replace(values.toVariableContext())

    fun replace(context: VariableReplacementContext): Expression<T>

    fun toString(vararg values: Pair<Variable<*>, String>): String =
            toString(values.toMap())

    fun toString(variableValues: Map<Variable<*>, String>): String

    fun defaultToString() =
            toString(variables().map { it to it.name }.toMap())

    fun variables(): Set<Variable<*>>

    fun dependsOn(variable: Variable<*>): Boolean =
            variable in variables()

}

class ConstantExpression<out T>(
        val value: T
): Expression<T> {

    override fun evaluate(context: VariableAssignmentContext) = value

    override fun replace(context: VariableReplacementContext): Expression<T> = this

    override fun toString(variableValues: Map<Variable<*>, String>): String = value.toString()

    override fun variables(): Set<Variable<*>> = emptySet()

}

fun Expression<RealNumber>.compute(vararg values: Pair<Variable<*>, Double>): Double =
        compute(values.toMap())

fun Expression<RealNumber>.compute(variableValues: Map<Variable<*>, Double>): Double =
        when(this) {
            is RealNumber -> getDouble()
            is Literal<RealNumber> -> value.getDouble()
            else ->
                evaluate(
                        variableValues.map { it.key assignTo it.value.asConstant() }.toVariableContext()
                ).getDouble()
        }


fun Expression<RealNumber>.asFunctionOf(variable: Variable<RealNumber>) = when {
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
