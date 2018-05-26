package tomasvolker.simboliko.function1

import tomasvolker.simboliko.asLiteral
import tomasvolker.simboliko.constant
import tomasvolker.simboliko.constant.RealConstant
import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.constant.Zero
import tomasvolker.simboliko.expression.*
import tomasvolker.simboliko.function1.operators.*
import tomasvolker.simboliko.function2.operators.*
import tomasvolker.simboliko.function2.*
import tomasvolker.simboliko.number.RealNumber


interface Function1<in I, out O>: (I)->O {

    override operator fun invoke(input: I): O

    operator fun invoke(input: Expression<I>): Expression<O>

    operator fun <I2> invoke(other: Function1<I2, I>) =
            compose(other)

    fun <I2> compose(other: Function1<I2, I>): Function1<I2, O> = TODO()

}

interface ConstantFunction<out O>: Function1<Any?, O>, Function2<Any?, Any?, O> {

    val value: O

    override operator fun invoke(input: Any?) = value

    override fun invoke(input1: Any?, input2: Any?) = value

    override operator fun invoke(input: Expression<Any?>) =
            ConstantExpression(value)

    override fun invoke(input1: Expression<Any?>, input2: Expression<Any?>) =
            ConstantExpression(value)

    override fun <I2> compose(other: Function1<I2, *>): ConstantFunction<O> =
            DefaultConstantFunction(value)

}

class DefaultConstantFunction<out O>(
        override val value: O
): ConstantFunction<O>

interface RealFunction1: Function1<RealNumber, RealNumber> {

    override fun invoke(input: RealNumber): RealNumber =
            ConstantRealExpression(invoke(input.asLiteral()))

    operator fun invoke(input: Double) =
            compute(input)

    fun compute(input: Double): Double

    fun toString(input: String): String

    override operator fun invoke(input: Expression<RealNumber>) =
            simplifyInvoke(input) ?:
            Function1Application(this, input)

    operator fun invoke(input: RealFunction1) =
            simplifyInvoke(input) ?: function1 { this(input(it)) }

    operator fun invoke(input: RealConstant): RealConstant =
            ConstantRealExpression(Function1Application(this, input))

    operator fun unaryPlus() = this

    operator fun unaryMinus(): RealFunction1 =
            function1 { -it }

    operator fun plus(other: RealFunction1) =
            simplifyPlus(other) ?: function1 { this(it) + other(it) }

    operator fun plus(other: Zero) = this

    operator fun minus(other: RealFunction1) =
        simplifyMinus(other) ?: function1 { this(it) - other(it) }

    operator fun minus(other: Zero) = this

    operator fun times(other: RealFunction1) =
            simplifyTimes(other) ?: function1 { this(it) * other(it) }

    operator fun times(other: One) = this

    operator fun times(other: Zero) = Zero

    operator fun div(other: RealFunction1): RealFunction1 =
            simplifyDiv(other) ?: function1 { this(it) / other(it) }

    operator fun div(other: One) = this

    operator fun plus(other: Int) = plus(constant(other))
    operator fun minus(other: Int) = minus(constant(other))
    operator fun times(other: Int) = times(constant(other))
    operator fun div(other: Int) = div(constant(other))

    fun defaultToString() = "x -> ${toString("x")}"

    // Simplifications

    fun simplifyInvoke(input: Expression<RealNumber>): Expression<RealNumber>? = when(input) {
        is RealConstant -> this(input)
        else -> null
    }

    fun simplifyInvoke(input: RealFunction1): RealFunction1? = when(input) {
        is RealConstant -> this(input)
        else -> null
    }

    fun simplifyPlus(other: RealFunction1): RealFunction1? = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyMinus(other: RealFunction1): RealFunction1? = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyTimes(other: RealFunction1): RealFunction1? = when(other) {
        is Zero -> Zero
        is One -> this
        else -> null
    }

    fun simplifyDiv(other: RealFunction1): RealFunction1? = when(other) {
        is One -> this
        else -> null
    }

}

interface DifferentiableFunction1: RealFunction1 {

    fun derivative(): DifferentiableFunction1

    fun derivativeAt(input: Double): Double = derivative()(input)

    operator fun invoke(input: DifferentiableFunction1): DifferentiableFunction1 =
            simplifyInvoke(input) as DifferentiableFunction1? ?:
            differentiableFunction1 { this(input(it)) }

    override operator fun unaryPlus() = this

    override operator fun unaryMinus() =
            differentiableFunction1 { -this(it) }

    operator fun plus(other: DifferentiableFunction1): DifferentiableFunction1 =
            simplifyPlus(other) as? DifferentiableFunction1 ?:
            differentiableFunction1 { this(it) + other(it) }

    operator fun minus(other: DifferentiableFunction1): DifferentiableFunction1 =
            differentiableFunction1 { this(it) - other(it) }

    operator fun times(other: DifferentiableFunction1): DifferentiableFunction1 =
            simplifyTimes(other) as? DifferentiableFunction1 ?:
            differentiableFunction1 { this(it) * other(it) }

    operator fun div(other: DifferentiableFunction1): DifferentiableFunction1 =
            simplifyDiv(other) as? DifferentiableFunction1 ?:
            differentiableFunction1 { this(it) / other(it) }

    override operator fun plus(other: Int) = plus(constant(other))
    override operator fun minus(other: Int) = minus(constant(other))
    override operator fun times(other: Int) = times(constant(other))
    override operator fun div(other: Int) = div(constant(other))

}

