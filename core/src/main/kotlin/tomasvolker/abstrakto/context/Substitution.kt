package tomasvolker.abstrakto.context

import tomasvolker.abstrakto.basic.Abstraction
import tomasvolker.abstrakto.basic.Variable

class SubstitutionContext(
        substitutions: Iterable<Substitution<*>>
) {

    private val substitutionMap: Map<Variable<*>, Substitution<*>> =
            substitutions.map { it.variable to it }.toMap()

    operator fun <T> get(variable: Variable<T>): Abstraction<T> =
            substitutionMap[variable]?.abstraction as? Abstraction<T> ?:
            variable

}

fun contextOf(vararg substitutions: Substitution<*>) =
        SubstitutionContext(substitutions.toList())

data class Substitution<T>(
        val variable: Variable<T>,
        val abstraction: Abstraction<T>
)

infix fun <T> Variable<T>.substituteWith(value: Abstraction<T>) = Substitution(this, value)