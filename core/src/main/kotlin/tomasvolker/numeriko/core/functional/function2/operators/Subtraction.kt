package tomasvolker.numeriko.core.functional.function2.operators

import tomasvolker.numeriko.core.functional.constant.MinusOne
import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.function2.*

object Subtraction: DifferentiableFunction2 {

    override fun invoke(input1: Double, input2: Double) = input1 - input2

    override fun derivative1() = One

    override fun derivative2() = MinusOne

    override fun toString(input1: String, input2: String) = "$input1 - ($input2)"

    override fun toString() = defaultToString()

}
