package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.affine.linear.ConstantFunction
import tomasvolker.numeriko.core.functional.affine.linear.NegateFunction
import tomasvolker.numeriko.core.functional.operators.*

interface Function/*: Evaluable*/ {
/*
    override fun evaluate(variableValues: Map<Variable, Double>) =
            TODO()

    val variables: Set<Variable>
    */
    operator fun invoke(input: Double): Double

    operator fun invoke(input: Function): Function =
            compose(this, input)

    operator fun invoke(input: ConstantFunction) =
            constant(this(input.value))

    operator fun unaryPlus(): Function = this

    operator fun unaryMinus(): Function =
            NegateFunction(this)

    operator fun plus(other: Function): Function = add(this, other)

    operator fun minus(other: Function): Function =
            Subtraction(this, other)

    operator fun times(other: Function): Function =
            Multiplication(this, other)

    operator fun div(other: Function): Function =
            Division(this, other)

    operator fun plus(other: Double): Function =
            this + other.asConstant()

    operator fun minus(other: Double): Function =
            this - other.asConstant()

    operator fun times(other: Double): Function =
            this * other.asConstant()

    operator fun div(other: Double): Function =
            this / other.asConstant()

    fun toString(input: String): String

}
