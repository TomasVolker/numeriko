package tomasvolker.abstrakto.context

import tomasvolker.abstrakto.basic.Variable

class Context(
        assignments: Iterable<Assignment<*>>
) {

    private val assignmentMap: Map<Variable<*>, Assignment<*>> =
            assignments.map { it.variable to it }.toMap()

    operator fun <T> get(variable: Variable<T>): T =
            assignmentMap[variable]?.value as? T ?:
            throw NoAssignmentForVariableException(variable)

}

class NoAssignmentForVariableException(
        val variable: Variable<*>
): Exception("No assignement for variable $variable.name")

fun emptyContext() = Context(emptyList())
fun contextOf(vararg assignments: Assignment<*>) =
        Context(assignments.toList())

data class Assignment<T>(
        val variable: Variable<T>,
        val value: T
)


infix fun <T> Variable<T>.assignTo(value: T) = Assignment(this, value)


