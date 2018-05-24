package tomasvolker.numeriko.core.functional.function1

import tomasvolker.numeriko.core.functional.constant
import tomasvolker.numeriko.core.functional.constant.Constant
import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.constant.Zero
import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function2.DifferentiableFunction2


interface Function1 {

    operator fun invoke(input: Double): Double

    fun toString(input: String): String

    operator fun invoke(input: Expression) =
            optimizeInvoke(input) ?: Function1Application(this, input)

    fun optimizeInvoke(input: Expression) = when(input) {
        is Constant -> this(input)
        else -> null
    }

    operator fun invoke(input: Function1) =
            optimizeInvoke(input) ?: function1 { this(input(it)) }

    fun optimizeInvoke(input: Function1) = when(input) {
        is Constant -> this(input)
        else -> null
    }

    operator fun invoke(input: Constant): Constant =
            constant(this(input.value))

    operator fun unaryPlus() = this

    operator fun unaryMinus(): Function1 =
            function1 { -it }

    operator fun plus(other: Function1) =
            optimizePlus(other) ?: function1 { this(it) + other(it) }

    fun optimizePlus(other: Function1) = when(other) {
        is Zero -> this
        else -> null
    }

    operator fun plus(other: Zero) = this

    operator fun minus(other: Function1) = when(other) {
        is Zero -> this
        else -> function1 { this(it) - other(it) }
    }

    operator fun minus(other: Zero) = this

    operator fun times(other: Function1) =
            optimizeTimes(other) ?:
            function1 { this(it) * other(it) }

    fun optimizeTimes(other: Function1) = when(other) {
        is Zero -> Zero
        is One -> this
        else -> null
    }

    operator fun times(other: One) = this

    operator fun times(other: Zero) = Zero

    operator fun div(other: Function1): Function1 =
            function1 { this(it) / other(it) }

    operator fun div(other: One) = this

}

interface DifferentiableFunction1: Function1 {

    fun derivative(): DifferentiableFunction1

    fun derivativeAt(input: Double): Double = derivative()(input)

    operator fun invoke(input: DifferentiableExpression) =
            optimizeInvoke(input) ?:
            DifferentiableFunction1Application(this, input)

    operator fun invoke(input: DifferentiableFunction1) =
            optimizeInvoke(input) ?:
            differentiableFunction1 { this(input(it)) }

    override operator fun unaryPlus() = this

    override operator fun unaryMinus() =
            differentiableFunction1 { -it }

    operator fun plus(other: DifferentiableFunction1) =
            optimizePlus(other) ?:
            differentiableFunction1 { this(it) + other(it) }

    operator fun minus(other: DifferentiableFunction1): DifferentiableFunction1 =
            differentiableFunction1 { this(it) - other(it) }

    operator fun times(other: DifferentiableFunction1): DifferentiableFunction1 =
            optimizeTimes(other) as? DifferentiableFunction1 ?:
            differentiableFunction1 { this(it) * other(it) }

    operator fun div(other: DifferentiableFunction1) =
            differentiableFunction1 { this(it) / other(it) }

}

fun Function1.defaultToString() = "x -> ${toString("x")}"
