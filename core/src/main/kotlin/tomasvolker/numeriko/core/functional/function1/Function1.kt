package tomasvolker.numeriko.core.functional.function1

import tomasvolker.numeriko.core.functional.expression.*
import tomasvolker.numeriko.core.functional.function1.operators.*
import tomasvolker.numeriko.core.functional.function2.*
import tomasvolker.numeriko.core.functional.function2.operators.*


interface Function1 {

    operator fun invoke(input: Double): Double

    fun toString(input: String): String

    operator fun invoke(input: Expression): Expression =
            Function1Application(this, input)

    operator fun invoke(input: Function1): Function1 =
            function1 { this(input(it)) }

    operator fun unaryPlus() = this

    operator fun unaryMinus(): Function1 =
            function1 { -it }

    operator fun plus(other: Function1): Function1 =
            function1 { this(it) + other(it) }

    operator fun minus(other: Function1): Function1 =
            function1 { this(it) - other(it) }

    operator fun times(other: Function1) =
            function1 { this(it) * other(it) }

    operator fun div(other: Function1): Function1 =
            function1 { this(it) / other(it) }

}

interface DifferentiableFunction1: Function1 {

    fun derivative(): DifferentiableFunction1

    fun derivativeAt(input: Double): Double = derivative()(input)

    operator fun invoke(input: DifferentiableExpression) =
            DifferentiableFunction1Application(this, input)

    operator fun invoke(input: DifferentiableFunction1) =
            differentiableFunction1 { this(input(it)) }

    override operator fun unaryPlus() = this

    override operator fun unaryMinus() =
            differentiableFunction1 { -it }

    operator fun plus(other: DifferentiableFunction1) =
            differentiableFunction1 { this(it) + other(it) }

    operator fun minus(other: DifferentiableFunction1): DifferentiableFunction1 =
            differentiableFunction1 { this(it) - other(it) }

    operator fun times(other: DifferentiableFunction1) =
            differentiableFunction1 { this(it) * other(it) }

    operator fun div(other: DifferentiableFunction1) =
            differentiableFunction1 { this(it) / other(it) }

}

fun Function1.defaultToString() = "x -> ${toString("x")}"
