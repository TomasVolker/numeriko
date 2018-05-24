package tomasvolker.numeriko.core.functional.operators

import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.expression.*

object Division: DifferentiableFunction2 {

    override fun derivative1() =
            differentiableFunction2 { x1, x2 -> 1 / x2 }

    override fun derivative2() =
            differentiableFunction2 { x1, x2 -> -x1 / (x2 * x2) }

    override fun invoke(input1: Double, input2: Double) = input1 / input2

    override fun toString(input1: String, input2: String) = "$input1 / $input2"

    override fun toString() = defaultToString()

}

operator fun Function1.div(other: Function1): Function1 =
        function1 { this(it) / other(it) }

operator fun DifferentiableFunction1.div(other: DifferentiableFunction1): DifferentiableFunction1 =
        differentiableFunction1 { this(it) / other(it) }

operator fun Expression.div(other: Expression) =
        Division(this, other)

operator fun DifferentiableExpression.div(other: DifferentiableExpression) =
        Division(this, other)
