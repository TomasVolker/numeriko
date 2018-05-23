package tomasvolker.numeriko.core.functional

interface Evaluable {

    fun evaluate(vararg values: Pair<Variable, Double>): Double =
            evaluate(values.toMap())

    fun evaluate(variableValues: Map<Variable, Double>): Double

    operator fun invoke(vararg values: Pair<Variable, Double>) =
            evaluate(*values)

    operator fun invoke(variableValues: Map<Variable, Double>) =
            evaluate(variableValues)

}

class NoValueForVariableException(val variable: Variable): Exception("Variable ${variable} has no value")

class Variable(
        val name: String
): Evaluable {

    override fun evaluate(variableValues: Map<Variable, Double>) =
            variableValues.getOrElse(this) {
                throw NoValueForVariableException(this)
            }

    override fun toString() = name

}

val X = Variable("x")