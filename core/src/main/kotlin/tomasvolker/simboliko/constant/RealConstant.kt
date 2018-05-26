package tomasvolker.simboliko.constant

import tomasvolker.simboliko.constant
import tomasvolker.simboliko.expression.Expression
import tomasvolker.simboliko.expression.variable.*
import tomasvolker.simboliko.function1.*
import tomasvolker.simboliko.function1.operators.NegateFunction
import tomasvolker.simboliko.function2.DifferentiableFunction2
import tomasvolker.simboliko.function2.operators.Addition
import tomasvolker.simboliko.function2.operators.Division
import tomasvolker.simboliko.function2.operators.Multiplication
import tomasvolker.simboliko.function2.operators.Subtraction
import tomasvolker.simboliko.number.RealNumber

interface RealConstant:
        RealNumber,
        DifferentiableFunction1,
        DifferentiableFunction2,
        Expression<RealNumber> {

    override fun getDouble(): Double

    //override fun invoke(input: Any) = this
    override fun invoke(input: RealNumber) = this
    override fun invoke(input: Expression<RealNumber>) = this
    //override fun <I2> compose(other: Function1<I2, RealNumber>) = this

/*
    override val y0 get() = getDouble()
    override val m get() = 0.0
*/

    override fun compute(input: Double) = getDouble()
    override fun compute(input1: Double, input2: Double) = getDouble()

    override fun replace(context: VariableReplacementContext) = this
    override fun evaluate(context: VariableAssignmentContext) = this

    override fun simplifyInvoke(input: Expression<RealNumber>) = this
    override fun simplifyInvoke(input: RealFunction1) = this

    override fun unaryPlus() = this
    override fun unaryMinus() = NegateFunction(this)
/*
    operator fun plus(other: RealConstant) = Addition(this, other as Expression<RealNumber>)
    operator fun minus(other: RealConstant) = Subtraction(this, other as Expression<RealNumber>)
    operator fun times(other: RealConstant) = Multiplication(this, other as Expression<RealNumber>)
    operator fun div(other: RealConstant) = Division(this, other as Expression<RealNumber>)

    override fun plus(other: Int) = plus(constant(other))
    override fun minus(other: Int) = plus(constant(other))
    override fun times(other: Int) = plus(constant(other))
    override fun div(other: Int) = plus(constant(other))
*/
    override fun derivative() = Zero
    override fun derivative1() = Zero
    override fun derivative2() = Zero

    override fun derivativeAt(input: Double) = 0.0

    override fun variables() = emptySet<Variable<RealNumber>>()

    override fun defaultToString() = getDouble().toString()
    override fun toString(input: String) = defaultToString()
    override fun toString(input1: String, input2: String) = defaultToString()
    override fun toString(variableValues: Map<Variable<*>, String>) = defaultToString()

}

class NumericConstant(
        val doubleValue: Double
) : RealConstant {

    override fun getDouble() = doubleValue

    override fun defaultToString() = doubleValue.toString()
    override fun toString() = defaultToString()

}

object Pi: RealConstant {

    override fun getDouble() = Math.PI

    override fun defaultToString() = "pi"

    override fun toString() = defaultToString()

}

object E: RealConstant {

    override fun getDouble() = Math.E

    override fun defaultToString() = "e"

    override fun toString() = defaultToString()

}