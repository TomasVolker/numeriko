package tomasvolker.simboliko.constant

import tomasvolker.simboliko.affine.AffineFunction
import tomasvolker.simboliko.constant
import tomasvolker.simboliko.expression.RealExpression
import tomasvolker.simboliko.expression.variable.RealVariable
import tomasvolker.simboliko.expression.variable.Variable
import tomasvolker.simboliko.expression.variable.VariableContext
import tomasvolker.simboliko.function1.RealFunction1
import tomasvolker.simboliko.function1.operators.NegateFunction
import tomasvolker.simboliko.function2.DifferentiableFunction2
import tomasvolker.simboliko.function2.operators.Addition
import tomasvolker.simboliko.function2.operators.Division
import tomasvolker.simboliko.function2.operators.Multiplication
import tomasvolker.simboliko.function2.operators.Subtraction

interface Constant: AffineFunction, DifferentiableFunction2, RealExpression {

    val doubleValue: Double

    override val y0 get() = doubleValue
    override val m get() = 0.0

    override fun compute(input: Double) = doubleValue
    override fun compute(input1: Double, input2: Double) = doubleValue

    override fun compute(variableValues: Map<Variable<*>, Double>) = doubleValue
    override fun evaluate(context: VariableContext) = this

    override fun simplifyInvoke(input: RealExpression) = this
    override fun simplifyInvoke(input: RealFunction1) = this

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

    override fun derivativeAt(input: Double) = 0.0

    override fun variables() = emptySet<RealVariable>()

    override fun defaultToString() = doubleValue.toString()
    override fun toString(input: String) = defaultToString()
    override fun toString(input1: String, input2: String) = defaultToString()
    override fun toString(variableValues: Map<Variable<*>, String>) = defaultToString()

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