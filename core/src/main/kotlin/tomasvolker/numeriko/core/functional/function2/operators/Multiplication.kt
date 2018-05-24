package tomasvolker.numeriko.core.functional.function2.operators

import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function1.*
import tomasvolker.numeriko.core.functional.function2.*

object Multiplication: DifferentiableFunction2 {

    override fun derivative1() = differentiableFunction2 { x1, x2 -> x2 }

    override fun derivative2() = differentiableFunction2 { x1, x2 -> x1 }

    override fun invoke(input1: Double, input2: Double) = input1 * input2

    override fun toString(input1: String, input2: String) = "$input1 * $input2"

    override fun toString() = defaultToString()

}