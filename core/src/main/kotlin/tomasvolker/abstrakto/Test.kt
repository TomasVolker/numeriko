package tomasvolker.abstrakto

import tomasvolker.abstrakto.common.evaluate
import tomasvolker.abstrakto.common.substitute
import tomasvolker.abstrakto.context.assignTo
import tomasvolker.abstrakto.context.contextOf
import tomasvolker.abstrakto.context.substituteWith
import tomasvolker.abstrakto.function.abstracted

fun main(args: Array<String>) {

    val function: (Double, Double)->Double = { x, y -> 3 * x + 2 * y }
    println(function)

    val abstractFunction = function.abstracted("f")
    println(abstractFunction)

    val x = "x".variableOf<Double>()
    val y = "y".variableOf<Double>()
    val abstractFunctionOf = abstractFunction(x, y)
    println(abstractFunctionOf)

    val abstractFunctionOfLiteral = substitute(
            abstractFunctionOf,
            contextOf(
                    x substituteWith 3.0.literal,
                    y substituteWith 2.0.literal
            )
    )
    println(abstractFunctionOfLiteral)

    val value = evaluate(
            abstractFunctionOf,
            contextOf(
                    x assignTo 3.0,
                    y assignTo 2.0
            )
    )
    println(value)

}
