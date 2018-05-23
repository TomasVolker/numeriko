package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.affine.linear.ConstantFunction
import tomasvolker.numeriko.core.functional.affine.linear.NegateFunction
import tomasvolker.numeriko.core.functional.operators.DifferentiableAddition
import tomasvolker.numeriko.core.functional.operators.DifferentiableDivision
import tomasvolker.numeriko.core.functional.operators.DifferentiableMultiplication
import tomasvolker.numeriko.core.functional.operators.compose

interface DifferentiableFunction: Function {

    override fun invoke(input: Function) = when(input) {
        is ConstantFunction -> invoke(input)
        is DifferentiableFunction -> invoke(input)
        else -> compose(this, input)
    }

    fun derivativeToLambda(): (Double)->Double = { derivativeAt(it) }

    operator fun invoke(input: DifferentiableFunction): DifferentiableFunction =
            composeDifferentiable(this, input)

    fun differentiate(): DifferentiableFunction

    fun derivativeAt(input: Double): Double = differentiate()(input)

    override operator fun unaryPlus(): DifferentiableFunction = this

    override operator fun unaryMinus(): DifferentiableFunction = NegateFunction(this)

    operator fun plus(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableAddition(this, other)

    operator fun minus(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableAddition(this, -other)

    operator fun times(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableMultiplication(this, other)

    operator fun div(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableDivision(this, other)

    override operator fun plus(other: Double): DifferentiableFunction =
            this + other.asConstant()

    override operator fun minus(other: Double): DifferentiableFunction =
            this - other.asConstant()

    override operator fun times(other: Double): DifferentiableFunction =
            this * other.asConstant()

    override operator fun div(other: Double): DifferentiableFunction =
            this / other.asConstant()

}
