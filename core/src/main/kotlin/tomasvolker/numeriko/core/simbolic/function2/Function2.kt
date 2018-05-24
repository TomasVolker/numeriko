package tomasvolker.numeriko.core.simbolic.function2

import tomasvolker.numeriko.core.simbolic.constant.Constant
import tomasvolker.numeriko.core.simbolic.expression.*

interface Function2 {

    operator fun invoke(input1: Double, input2: Double): Double

    fun toString(input1: String, input2: String): String

    operator fun invoke(input1: Expression, input2: Expression): Expression =
            simplifyInvoke(input1, input2) ?:
            Function2Application(this, input1, input2)

    fun simplifyInvoke(input1: Expression, input2: Expression): Expression? = when {
        input1 is Constant && input2 is Constant -> invoke(input1, input2)
        else -> null
    }

    operator fun invoke(input1: Constant, input2: Constant): Constant =
            ConstantExpression(Function2Application(this, input1, input2))

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
            simplifyInvoke(input1, input2) as? DifferentiableExpression ?:
            DifferentiableFunction2Application(this, input1, input2)

}


fun Function2.defaultToString() = "x1, x2 -> ${toString("x1", "x2")}"


