package tomasvolker.simboliko.function2.operators

import tomasvolker.simboliko.constant.MinusOne
import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.function2.DifferentiableFunction2
import tomasvolker.simboliko.function2.defaultToString
import tomasvolker.simboliko.number.RealNumber

object Subtraction: DifferentiableFunction2 {

    override fun compute(input1: Double, input2: Double) = input1 - input2

    override fun derivative1() = One

    override fun derivative2() = MinusOne

    override fun toString(input1: String, input2: String) = "$input1 - ($input2)"

    override fun toString() = defaultToString()

}

operator fun Expression<RealNumber>.minus(other: Expression<RealNumber>) =
        Subtraction(this, other)