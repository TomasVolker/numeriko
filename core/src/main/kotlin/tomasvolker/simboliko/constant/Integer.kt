package tomasvolker.simboliko.constant

import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.function1.operators.NegateFunction
import tomasvolker.simboliko.number.RealNumber

interface IntegerConstant: RealConstant {

    val integerValue: Long
    override fun getDouble() = integerValue.toDouble()

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

object Zero: IntegerConstant {

    override val integerValue get() = 0L

    override fun invoke(input: Double) = 0.0
/*
    override fun simplifyPlus(other: RealFunction1) = other
    override fun simplifyPlus(other: Expression<RealNumber>) = other

    override fun simplifyTimes(other: RealFunction1) = Zero
    override fun simplifyTimes(other: Expression<RealNumber>) = Zero
*/
    override fun derivative() = Zero

    override fun toString() = defaultToString()

}

object One: IntegerConstant {

    override val integerValue get() = 1L
/*
    override fun simplifyTimes(other: RealFunction1) = other
    override fun simplifyTimes(other: Expression<RealNumber>) = other
*/
    override fun toString() = defaultToString()

}

object MinusOne: IntegerConstant {

    override val integerValue get() = -1L
/*
    override fun simplifyTimes(other: RealFunction1) = NegateFunction(other)
    override fun simplifyTimes(other: Expression<RealNumber>) = NegateFunction(other)
*/
    override fun defaultToString() = "(-1)"

    override fun toString() = defaultToString()

}
