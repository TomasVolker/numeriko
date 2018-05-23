package tomasvolker.numeriko.core.functional.affine.linear

import tomasvolker.numeriko.core.functional.affine.AffineFunction
import tomasvolker.numeriko.core.functional.DifferentiableFunction
import tomasvolker.numeriko.core.functional.Function

interface ConstantFunction: AffineFunction {
    val value: Double

    override val y0 get() = value
    override val m get() = 0.0

    override fun invoke(input: Double) = value

    override fun invoke(input: Function) = this
    override fun invoke(input: DifferentiableFunction) = this
    override fun invoke(input: AffineFunction) = this
    override fun invoke(input: ConstantFunction) = this

    override fun differentiate() = ZeroFunction

    override fun derivativeAt(input: Double) = 0.0

    override fun toString(input: String) = "$value"

}

class DefaultConstantFunction(
        override val value: Double
) : ConstantFunction {

    override fun toString() = toString("x")

}

interface IntegerConstantFunction: ConstantFunction {

    val integerValue: Long
    override val value: Double get() = integerValue.toDouble()

    override fun invoke(input: Function) = this
    override fun invoke(input: DifferentiableFunction) = this
    override fun invoke(input: AffineFunction) = this
    override fun invoke(input: ConstantFunction) = this

    override fun toString(input: String) = "$integerValue"

}

class DefaultIntegerConstantFunction(
        override val integerValue: Long
) : IntegerConstantFunction {

    override fun equals(other: Any?) = when(other) {
        is IntegerConstantFunction -> this.integerValue == other.integerValue
        else -> false
    }

    override fun hashCode() = integerValue.hashCode()

    override fun toString() = toString("x")

}

object ZeroFunction: IntegerConstantFunction, LinearFunction {

    override val y0: Double get() = 0.0
    override val integerValue get() = 0L

    override fun invoke(input: Double) = 0.0

    override fun invoke(input: Function) = ZeroFunction
    override fun invoke(input: DifferentiableFunction) = ZeroFunction
    override fun invoke(input: AffineFunction) = ZeroFunction
    override fun invoke(input: LinearFunction) = ZeroFunction
    override fun invoke(input: ConstantFunction) = ZeroFunction

    override fun differentiate() = ZeroFunction

    override fun toString() = toString("x")
    override fun toString(input: String) = "0"

    override fun plus(other: Function) = other
    override fun minus(other: Function) = -other
    override fun times(other: Function) = ZeroFunction
    override fun div(other: DifferentiableFunction) = ZeroFunction

}

object OneFunction: IntegerConstantFunction {

    override val integerValue get() = 1L

    override fun invoke(input: Function) = OneFunction
    override fun invoke(input: DifferentiableFunction) = OneFunction
    override fun invoke(input: AffineFunction) = OneFunction
    override fun invoke(input: ConstantFunction) = OneFunction

    override fun toString() = toString("x")
    override fun toString(input: String) = "1"

    override fun times(other: Function) = other

}

object MinusOneFunction: IntegerConstantFunction {

    override val integerValue get() = -1L

    override fun invoke(input: Function) = MinusOneFunction
    override fun invoke(input: DifferentiableFunction) = MinusOneFunction
    override fun invoke(input: AffineFunction) = MinusOneFunction
    override fun invoke(input: ConstantFunction) = MinusOneFunction

    override fun toString() = toString("x")
    override fun toString(input: String) = "(-1)"

}