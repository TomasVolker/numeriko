package tomasvolker.simboliko.expression.variable

import tomasvolker.simboliko.expression.Expression

class Variable<out T>(
        val name: String
): Expression<T> {

    override fun evaluate(context: VariableAssignmentContext) =
            context[this]

    override fun replace(context: VariableReplacementContext) =
            context[this]

    override fun variables() = setOf(this)

    override fun toString(variableValues: Map<Variable<*>, String>) =
            variableValues.getOrElse(this) {
                throw NoVariableAssignmentException(this)
            }

    override fun toString() = name

    override fun equals(other: Any?) =
            other is Variable<*> && this.name == other.name

    override fun hashCode() = name.hashCode()

}

class VariableAssignmentContext(
        variableAssignments: Iterable<VariableAssignment<*>>
) {

    private val variableAssignmentMap: Map<Variable<*>, Any?> =
            variableAssignments.map { it.variable to it.value }.toMap()

    operator fun <T> get(variable: Variable<T>): T =
            getValue(variable)

    fun <T> getValue(variable: Variable<T>): T =
            variableAssignmentMap[variable] as? T ?:
            throw NoVariableAssignmentException(variable)

}

class VariableReplacementContext(
        variableAssignments: Iterable<VariableReplacement<*>>
) {

    private val variableAssignmentMap: Map<Variable<*>, VariableReplacement<*>> =
            variableAssignments.map { it.variable to it }.toMap()

    operator fun <T> get(variable: Variable<T>): Expression<T> =
            getExpression(variable)

    fun <T> getExpression(variable: Variable<T>): Expression<T> =
            variableAssignmentMap[variable]?.expression as? Expression<T> ?: variable

}

class VariableReplacement<out T>(
        val variable: Variable<T>,
        val expression: Expression<T>
)

class VariableAssignment<out T>(
        val variable: Variable<T>,
        val value: T
)

fun Array<out VariableAssignment<*>>.toVariableContext() =
        toList().toVariableContext()

fun Iterable<VariableAssignment<*>>.toVariableContext() =
        VariableAssignmentContext(this)

fun Array<out VariableReplacement<*>>.toVariableContext() =
        toList().toVariableContext()

fun Iterable<VariableReplacement<*>>.toVariableContext() =
        VariableReplacementContext(this)


infix fun <T> Variable<T>.replaceWith(expression: Expression<T>) =
        VariableReplacement(this, expression)

infix fun <T> Variable<T>.assignTo(value: T) =
        VariableAssignment(this, value)


class NoVariableAssignmentException(
        val variable: Variable<*>
): Exception("RealVariable ${variable.name} has no value")
