package tomasvolker.numeriko.core.functional.function2

import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.DifferentiableFunction2Application
import tomasvolker.numeriko.core.functional.expression.Expression
import tomasvolker.numeriko.core.functional.expression.Function2Application

interface Function2 {

    operator fun invoke(input1: Double, input2: Double): Double

    fun toString(input1: String, input2: String): String

    operator fun invoke(input1: Expression, input2: Expression): Expression =
            Function2Application(this, input1, input2)

}

interface DifferentiableFunction2: Function2 {

    fun derivative1(): DifferentiableFunction2

    fun derivative2(): DifferentiableFunction2

    fun derivative1At(input1: Double, input2: Double): Double = derivative1()(input1, input2)

    fun derivative2At(input1: Double, input2: Double): Double = derivative2()(input1, input2)

    operator fun invoke(
            input1: DifferentiableExpression,
            input2: DifferentiableExpression
    ): DifferentiableExpression =
            DifferentiableFunction2Application(this, input1, input2)

}


fun Function2.defaultToString() = "x1, x2 -> ${toString("x1", "x2")}"


