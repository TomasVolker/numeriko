package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.expression.DifferentiableExpression
import tomasvolker.numeriko.core.functional.expression.DifferentiableFunction1Application

interface DifferentiableFunction1: Function1 {

    fun derivative(): DifferentiableFunction1

    //fun derivativeAt(input: Double): Double = derivative()(input)

}

operator fun DifferentiableFunction1.invoke(input: DifferentiableExpression) =
        DifferentiableFunction1Application(this, input)