package tomasvolker.abstrakto.function

import tomasvolker.abstrakto.basic.Abstraction
import tomasvolker.abstrakto.basic.Application1

typealias AbstractFunction1<I, O> = (Abstraction<I>)-> Abstraction<O>

class AbstractedFunction1<in I, out O>(
        val name: String? = null,
        val function: (I)->O
): AbstractFunction1<I, O> {

    override fun invoke(p1: Abstraction<I>): Abstraction<O> =
            Application1(function, p1, name)

    override fun toString(): String = name ?: function.toString()

}

fun <I, O> abstracted(name: String, function: (I)->O): AbstractFunction1<I, O> =
        AbstractedFunction1(name, function)

fun <I, O> ((I)->O).abstracted(name: String = "f"): AbstractFunction1<I, O> =
        abstracted(name, this)