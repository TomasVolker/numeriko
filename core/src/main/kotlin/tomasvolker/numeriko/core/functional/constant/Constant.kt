package tomasvolker.numeriko.core.functional.constant

import tomasvolker.numeriko.core.functional.affine.AffineFunction
import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.Variable
import tomasvolker.numeriko.core.functional.function2.DifferentiableFunction2

interface Constant: AffineFunction, DifferentiableFunction2, DifferentiableExpression {

    val value: Double

    override val y0 get() = value
    override val m get() = 0.0

    override fun invoke(input: Double) = value
    override fun invoke(input1: Double, input2: Double) = value

    override fun derivative() = Zero
    override fun derivative1() = Zero
    override fun derivative2() = Zero

    override fun derivative(withRespectTo: Variable) = Zero

    //override fun derivativeAt(input: Double) = 0.0

    override fun variables() = emptySet<Variable>()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            value

    override fun toString(input: String) = defaultToString()
    override fun toString(input1: String, input2: String) = defaultToString()

    override fun toString(variableValues: Map<Variable, String>) = defaultToString()

    fun defaultToString() = value.toString()

}

class DefaultConstant(
        override val value: Double
) : Constant {

    override fun toString() = defaultToString()

}

object Pi: Constant {

    override val value: Double = Math.PI

    override fun defaultToString() = "pi"

    override fun toString() = defaultToString()

}

object E: Constant {

    override val value: Double = Math.E

    override fun defaultToString() = "e"

    override fun toString() = defaultToString()

}