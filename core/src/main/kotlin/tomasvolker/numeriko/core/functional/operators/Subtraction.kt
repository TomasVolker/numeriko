package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.expression.*

object Subtraction: DifferentiableFunction2 {

    override fun invoke(input1: Double, input2: Double) = input1 - input2

    override fun derivative1() = One

    override fun derivative2() = MinusOne

    override fun toString(input1: String, input2: String) = "$input1 - ($input2)"

    override fun toString() = defaultToString()

}

operator fun Function1.minus(other: Function1): Function1 =
        function1 { this(it) - other(it) }

operator fun DifferentiableFunction1.minus(other: DifferentiableFunction1): DifferentiableFunction1 =
        differentiableFunction1 { this(it) - other(it) }

operator fun Expression.minus(other: Expression) =
        Subtraction(this, other)

operator fun DifferentiableExpression.minus(other: DifferentiableExpression) =
        Subtraction(this, other)
