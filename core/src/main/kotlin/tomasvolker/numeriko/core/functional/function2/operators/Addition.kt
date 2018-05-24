package tomasvolker.numeriko.core.functional.function2.operators

import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function1.*
import tomasvolker.numeriko.core.functional.function2.*

object Addition: DifferentiableFunction2 {

    override fun invoke(input1: Double, input2: Double) = input1 + input2

    override fun derivative1() = One

    override fun derivative2() = One

    override fun toString(input1: String, input2: String) = "($input1 + $input2)"

    override fun toString() = defaultToString()

}

operator fun Function1.plus(other: Function1): Function1 =
        function1 { this(it) + other(it) }

operator fun DifferentiableFunction1.plus(other: DifferentiableFunction1): DifferentiableFunction1 =
        differentiableFunction1 { this(it) + other(it) }

operator fun Expression.plus(other: Expression) =
        Addition(this, other)

operator fun DifferentiableExpression.plus(other: DifferentiableExpression) =
        Addition(this, other)
