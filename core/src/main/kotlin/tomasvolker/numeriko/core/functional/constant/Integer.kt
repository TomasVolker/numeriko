package tomasvolker.numeriko.core.functional.constant

import tomasvolker.numeriko.core.functional.affine.LinearFunction

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

    override fun toString() = defaultToString()

}

object One: IntegerConstant {

    override val integerValue get() = 1L

    override fun toString() = defaultToString()

}

object MinusOne: IntegerConstant {

    override val integerValue get() = -1L

    override fun defaultToString() = "(-1)"

    override fun toString() = defaultToString()

}
