package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.affine.AffineFunction
import tomasvolker.numeriko.core.functional.affine.linear.LinearFunction
import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Variable

interface Constant: AffineFunction, DifferentiableFunction2, DifferentiableExpression {

    val value: Double

    override val y0 get() = value
    override val m get() = 0.0

    override fun invoke(input: Double) = value
    override fun invoke(input1: Double, input2: Double) = value

    override fun derivative() = Zero
    override fun derivative1() = Zero
    override fun derivative2() = Zero

    override fun derivative(withRespectTo: Variable) = Zero

    //override fun derivativeAt(input: Double) = 0.0

    override fun dependsOn() = emptySet<Variable>()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            value

    override fun toString(input: String) = defaultToString()
    override fun toString(input1: String, input2: String) = defaultToString()

    override fun toString(variableValues: Map<Variable, String>) = defaultToString()

    fun defaultToString() = value.toString()

}

class DefaultConstant(
        override val value: Double
) : Constant {

    override fun toString() = defaultToString()

}

interface IntegerConstant: Constant {

    val integerValue: Long
    override val value: Double get() = integerValue.toDouble()

    override fun defaultToString() = "$integerValue"

}

class DefaultIntegerConstant(
        override val integerValue: Long
) : IntegerConstant {

    override fun equals(other: Any?) = when(other) {
        is IntegerConstant -> this.integerValue == other.integerValue
        else -> false
    }

    override fun hashCode() = integerValue.hashCode()

    override fun toString() = defaultToString()

}

object Zero: IntegerConstant, LinearFunction {

    override val y0: Double get() = 0.0
    override val integerValue get() = 0L

    override fun invoke(input: Double) = 0.0

    override fun derivative() = Zero

    override fun toString(input: String) = "0"

    override fun toString() = defaultToString()

}

object One: IntegerConstant {

    override val integerValue get() = 1L

    override fun toString(input: String) = "1"

    override fun toString() = defaultToString()

}

object MinusOne: IntegerConstant {

    override val integerValue get() = -1L

    override fun toString(input: String) = "(-1)"

    override fun toString() = defaultToString()

}