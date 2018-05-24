package tomasvolker.numeriko.core.functional.function1

import tomasvolker.numeriko.core.functional.expression.*

interface Function1 {

    operator fun invoke(input: Double): Double

    fun toString(input: String): String

}

interface DifferentiableFunction1: Function1 {

    fun derivative(): DifferentiableFunction1

    //fun derivativeAt(input: Double): Double = derivative()(input)

}

fun Function1.defaultToString() = "x -> ${toString("x")}"

operator fun Function1.invoke(input: Expression): Expression =
        Function1Application(this, input)

operator fun Function1.invoke(input: Function1): Function1 =
        function1 { this(input(it)) }

operator fun DifferentiableFunction1.invoke(input: DifferentiableExpression) =
        DifferentiableFunction1Application(this, input)

operator fun DifferentiableFunction1.invoke(input: DifferentiableFunction1) =
        differentiableFunction1 { this(input(it)) }