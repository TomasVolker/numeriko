package tomasvolker.simboliko.expression.variable

import tomasvolker.simboliko.expression.Expression

interface Variable<out T>: Expression<T> {

    val name: String

    override fun evaluate(context: VariableContext) =
            context[this]

}

class VariableContext(
        variableAssignments: Iterable<VariableAssignment<*>>
) {

    private val variableAssignmentMap: Map<Variable<*>, VariableAssignment<*>> =
            variableAssignments.map { it.variable to it }.toMap()

    operator fun <T> get(variable: Variable<T>): Expression<T> =
            getExpression(variable)

    fun <T> getExpression(variable: Variable<T>): Expression<T> =
            variableAssignmentMap.get(variable)?.expression as? Expression<T> ?: variable

}

class VariableAssignment<T>(
        val variable: Variable<T>,
        val expression: Expression<T>
)

infix fun <T> Variable<T>.assignTo(expression: Expression<T>) =
        VariableAssignment(this, expression)

fun Array<out VariableAssignment<*>>.toVariableContext() =
        toList().toVariableContext()

fun Iterable<VariableAssignment<*>>.toVariableContext() =
        VariableContext(this)


class NoVariableAssignmentException(
        val variable: Variable<*>
): Exception("RealVariable ${variable.name} has no value")
