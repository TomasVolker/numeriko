package tomasvolker.numeriko.core.simbolic.constant

import tomasvolker.numeriko.core.simbolic.affine.AffineFunction
import tomasvolker.numeriko.core.simbolic.constant
import tomasvolker.numeriko.core.simbolic.expression.DifferentiableExpression
import tomasvolker.numeriko.core.simbolic.expression.Expression
import tomasvolker.numeriko.core.simbolic.expression.Variable
import tomasvolker.numeriko.core.simbolic.function1.Function1
import tomasvolker.numeriko.core.simbolic.function1.operators.NegateFunction
import tomasvolker.numeriko.core.simbolic.function2.DifferentiableFunction2
import tomasvolker.numeriko.core.simbolic.function2.operators.Addition
import tomasvolker.numeriko.core.simbolic.function2.operators.Division
import tomasvolker.numeriko.core.simbolic.function2.operators.Multiplication
import tomasvolker.numeriko.core.simbolic.function2.operators.Subtraction

interface Constant: AffineFunction, DifferentiableFunction2, DifferentiableExpression {

    val doubleValue: Double

    override val y0 get() = doubleValue
    override val m get() = 0.0

    override fun invoke(input: Double) = doubleValue
    override fun invoke(input1: Double, input2: Double) = doubleValue

    override fun simplifyInvoke(input: Expression) = this
    override fun simplifyInvoke(input: Function1) = this

    override fun unaryPlus() = this
    override fun unaryMinus() = NegateFunction(this)

    operator fun plus(other: Constant) = Addition(this, other)
    operator fun minus(other: Constant) = Subtraction(this, other)
    operator fun times(other: Constant) = Multiplication(this, other)
    operator fun div(other: Constant) = Division(this, other)

    override fun plus(other: Int) = plus(constant(other))
    override fun minus(other: Int) = plus(constant(other))
    override fun times(other: Int) = plus(constant(other))
    override fun div(other: Int) = plus(constant(other))

    override fun derivative() = Zero
    override fun derivative1() = Zero
    override fun derivative2() = Zero

    override fun derivative(withRespectTo: Variable) = Zero

    override fun derivativeAt(input: Double) = 0.0

    override fun variables() = emptySet<Variable>()

    override fun evaluate(variableValues: Map<Variable, Double>) =
            doubleValue

    override fun defaultToString() = doubleValue.toString()
    override fun toString(input: String) = defaultToString()
    override fun toString(input1: String, input2: String) = defaultToString()
    override fun toString(variableValues: Map<Variable, String>) = defaultToString()

}

class NumericConstant(
        override val doubleValue: Double
) : Constant {

    override fun defaultToString() = doubleValue.toString()
    override fun toString() = defaultToString()

}

object Pi: Constant {

    override val doubleValue: Double = Math.PI

    override fun defaultToString() = "pi"

    override fun toString() = defaultToString()

}

object E: Constant {

    override val doubleValue: Double = Math.E

    override fun defaultToString() = "e"

    override fun toString() = defaultToString()

}