package tomasvolker.numeriko.core.simbolic.function2.operators

import tomasvolker.numeriko.core.simbolic.*
import tomasvolker.numeriko.core.simbolic.constant.One
import tomasvolker.numeriko.core.simbolic.expression.Expression
import tomasvolker.numeriko.core.simbolic.function2.*

object Division: DifferentiableFunction2 {

    override fun compute(input1: Double, input2: Double) = input1 / input2

    override fun derivative1() =
            differentiableFunction2 { x1, x2 -> 1 / x2 }

    override fun derivative2() =
            differentiableFunction2 { x1, x2 -> -x1 / (x2 * x2) }

    override fun toString(input1: String, input2: String) = "$input1 / ($input2)"

    override fun toString() = defaultToString()

    override fun simplifyInvoke(input1: Expression, input2: Expression) =
            super.simplifyInvoke(input1, input2) ?:
            when {
                input2 is One -> input1
                else -> null
            }

}

