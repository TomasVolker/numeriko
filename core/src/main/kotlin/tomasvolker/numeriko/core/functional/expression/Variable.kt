package tomasvolker.numeriko.core.functional.expression

import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.constant.Zero

class Variable(
        val name: String
): DifferentiableExpression {

    override fun evaluate(variableValues: Map<Variable, Double>) =
            variableValues.getOrElse(this) {
                throw NoValueForVariableException(this)
            }

    override fun derivative(withRespectTo: Variable) =
            if (this == withRespectTo) One else Zero

    override fun variables() = setOf(this)

    override fun toString(variableValues: Map<Variable, String>) =
            variableValues.getOrElse(this) {
                throw NoValueForVariableException(this)
            }

    override fun toString() = name

    override fun equals(other: Any?) =
            other is Variable && other.name == this.name

    override fun hashCode() = name.hashCode()

}


class NoValueForVariableException(
        val variable: Variable
): Exception("Variable ${variable} has no doubleValue")