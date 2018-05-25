package tomasvolker.simboliko.function1

import tomasvolker.simboliko.constant
import tomasvolker.simboliko.constant.Constant
import tomasvolker.simboliko.constant.One
import tomasvolker.simboliko.constant.Zero
import tomasvolker.simboliko.expression.*


interface Function1<in I, out O> {

    operator fun invoke(input: I): O

}

interface RealFunction1 {

    operator fun invoke(input: Double) =
            compute(input)

    fun compute(input: Double): Double

    fun toString(input: String): String

    operator fun invoke(input: RealExpression) =
            simplifyInvoke(input) ?: Function1Application(this, input)

    operator fun invoke(input: RealFunction1) =
            simplifyInvoke(input) ?: function1 { this(input(it)) }

    operator fun invoke(input: Constant): Constant =
            ConstantExpression(Function1Application(this, input))

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

    fun simplifyInvoke(input: RealExpression): RealExpression? = when(input) {
        is Constant -> this(input)
        else -> null
    }

    fun simplifyInvoke(input: RealFunction1): RealFunction1? = when(input) {
        is Constant -> this(input)
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

