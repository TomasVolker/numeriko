package tomasvolker.numeriko.core.simbolic.function1

import tomasvolker.numeriko.core.simbolic.constant
import tomasvolker.numeriko.core.simbolic.constant.Constant
import tomasvolker.numeriko.core.simbolic.constant.One
import tomasvolker.numeriko.core.simbolic.constant.Zero
import tomasvolker.numeriko.core.simbolic.expression.*


interface Function1 {

    operator fun invoke(input: Double) =
            compute(input)

    fun compute(input: Double): Double

    fun toString(input: String): String

    operator fun invoke(input: Expression) =
            simplifyInvoke(input) ?:
            Function1Application(this, input)

    operator fun invoke(input: Function1) =
            simplifyInvoke(input) ?:
            function1 { this(input(it)) }

    operator fun invoke(input: Constant): Constant =
            ConstantExpression(Function1Application(this, input))

    operator fun unaryPlus() = this

    operator fun unaryMinus(): Function1 =
            function1 { -it }

    operator fun plus(other: Function1) =
            simplifyPlus(other) ?:
            function1 { this(it) + other(it) }

    operator fun plus(other: Zero) = this

    operator fun minus(other: Function1) =
        simplifyMinus(other) ?:
        function1 { this(it) - other(it) }

    operator fun minus(other: Zero) = this

    operator fun times(other: Function1) =
            simplifyTimes(other) ?:
            function1 { this(it) * other(it) }

    operator fun times(other: One) = this

    operator fun times(other: Zero) = Zero

    operator fun div(other: Function1): Function1 =
            simplifyDiv(other) ?:
            function1 { this(it) / other(it) }

    operator fun div(other: One) = this

    operator fun plus(other: Int) = plus(constant(other))
    operator fun minus(other: Int) = minus(constant(other))
    operator fun times(other: Int) = times(constant(other))
    operator fun div(other: Int) = div(constant(other))

    fun defaultToString() = "x -> ${toString("x")}"

    // Simplifications

    fun simplifyInvoke(input: Expression) = when(input) {
        is Constant -> this(input)
        else -> null
    }

    fun simplifyInvoke(input: Function1) = when(input) {
        is Constant -> this(input)
        else -> null
    }

    fun simplifyPlus(other: Function1) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyMinus(other: Function1) = when(other) {
        is Zero -> this
        else -> null
    }

    fun simplifyTimes(other: Function1) = when(other) {
        is Zero -> Zero
        is One -> this
        else -> null
    }

    fun simplifyDiv(other: Function1) = when(other) {
        is One -> this
        else -> null
    }

}

interface DifferentiableFunction1: Function1 {

    fun derivative(): DifferentiableFunction1

    fun derivativeAt(input: Double): Double = derivative()(input)

    operator fun invoke(input: DifferentiableExpression): DifferentiableExpression =
            simplifyInvoke(input) ?:
            DifferentiableFunction1Application(this, input)

    operator fun invoke(input: DifferentiableFunction1): DifferentiableFunction1 =
            simplifyInvoke(input) ?:
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

