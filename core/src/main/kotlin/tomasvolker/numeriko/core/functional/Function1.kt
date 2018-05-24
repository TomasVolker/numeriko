package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.expression.Expression
import tomasvolker.numeriko.core.functional.expression.Function1Application

interface Function1 {

    operator fun invoke(input: Double): Double

    fun toString(input: String): String

}

fun Function1.defaultToString() = "x -> ${toString("x")}"

operator fun Function1.invoke(input: Expression): Expression =
        Function1Application(this, input)
