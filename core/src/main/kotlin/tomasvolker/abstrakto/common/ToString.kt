package tomasvolker.abstrakto.common

import tomasvolker.abstrakto.basic.Abstraction
import tomasvolker.abstrakto.basic.Application1
import tomasvolker.abstrakto.basic.Application2
import tomasvolker.abstrakto.basic.Literal
import tomasvolker.abstrakto.basic.Variable
import tomasvolker.abstrakto.context.Context
import tomasvolker.abstrakto.context.SubstitutionContext

fun printToString(abstraction: Abstraction<*>): String =
        when (abstraction) {
            is Literal<*> -> abstraction.value.toString()
            is Variable<*> -> abstraction.name
            is Application1<*, *> ->
                "${abstraction.functionName}(${printToString(abstraction.input)})"
            is Application2<*, *, *> ->
                "${abstraction.functionName}(${printToString(abstraction.input1)}, ${printToString(abstraction.input2)})"
        }

fun <T> evaluate(abstraction: Abstraction<T>, context: Context): T =
        when (abstraction) {
            is Literal<*> -> {
                with(abstraction as Literal<T>) {
                    value
                }
            }
            is Variable<*> -> {
                with(abstraction as Variable<T>) {
                    context[abstraction]
                }
            }
            is Application1<*, *> -> {
                with(abstraction as Application1<Any?, T>) {
                    function(evaluate(input, context))
                }
            }
            is Application2<*, *, *> -> {
                with(abstraction as Application2<Any?, Any?, T>) {
                    function(
                            evaluate(input1, context),
                            evaluate(input2, context)
                    )
                }
            }
        }

fun <T> substitute(abstraction: Abstraction<T>, context: SubstitutionContext): Abstraction<T> =
        when (abstraction) {
            is Literal<*> -> abstraction
            is Variable<*> -> {
                with(abstraction as Variable<T>) {
                    context[abstraction]
                }
            }
            is Application1<*, *> -> {
                with(abstraction as Application1<Any?, T>) {
                    Application1(
                            function,
                            substitute(input, context),
                            functionName
                    )
                }
            }
            is Application2<*, *, *> -> {
                with(abstraction as Application2<Any?, Any?, T>) {
                    Application2(
                            function,
                            substitute(input1, context),
                            substitute(input2, context),
                            functionName
                    )
                }
            }
        }