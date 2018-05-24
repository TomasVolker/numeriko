package tomasvolker.numeriko.core.functional.expression

interface Expression {

    fun evaluate(vararg values: Pair<Variable, Double>): Double =
            evaluate(values.toMap())

    fun evaluate(variableValues: Map<Variable, Double>): Double

    fun dependsOn(): Set<Variable>

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
        toString(dependsOn().map { it to it.name }.toMap())

