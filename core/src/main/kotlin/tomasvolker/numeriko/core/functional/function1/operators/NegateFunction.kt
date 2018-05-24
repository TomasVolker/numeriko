package tomasvolker.numeriko.core.functional.function1.operators

import tomasvolker.numeriko.core.functional.affine.LinearFunction
import tomasvolker.numeriko.core.functional.constant.MinusOne
import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function1.*

object NegateFunction: LinearFunction {

    override val m: Double get() = -1.0

    override fun invoke(input: Double) = -input

    override fun derivative() = MinusOne
    //override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = "(-${input})"

}

operator fun Expression.unaryMinus(): Expression =
        NegateFunction(this)

operator fun Function1.unaryMinus(): Function1 =
        function1 { -it }

operator fun DifferentiableExpression.unaryMinus(): DifferentiableExpression =
        NegateFunction(this)

operator fun DifferentiableFunction1.unaryMinus(): DifferentiableFunction1 =
        differentiableFunction1 { -it }
