package tomasvolker.simboliko.function2.operators

import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function1.operators.unaryMinus
import tomasvolker.simboliko.function2.DifferentiableFunction2
import tomasvolker.simboliko.function2.defaultToString
import tomasvolker.simboliko.function2.differentiableFunction2
import tomasvolker.simboliko.number.RealNumber
import tomasvolker.simboliko.*

object Division: DifferentiableFunction2 {

    override fun compute(input1: Double, input2: Double) = input1 / input2

    override fun derivative1() =
            differentiableFunction2 { x1, x2 -> 1 / x2 }

    override fun derivative2() =
            differentiableFunction2 { x1, x2 -> -x1 / (x2 * x2) }

    override fun toString(input1: String, input2: String) = "$input1 / ($input2)"

    override fun toString() = defaultToString()

}

operator fun Expression<RealNumber>.div(other: Expression<RealNumber>) =
        Division(this, other)