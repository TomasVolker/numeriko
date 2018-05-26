package tomasvolker.abstrakto.function

import tomasvolker.abstrakto.basic.Abstraction
import tomasvolker.abstrakto.basic.Application2

typealias AbstractFunction2<I1, I2, O> = (Abstraction<I1>, Abstraction<I2>)-> Abstraction<O>

class AbstractedFunction2<in I1, in I2, out O>(
        val name: String? = null,
        val function: (I1, I2)->O
): AbstractFunction2<I1, I2, O> {

    override fun invoke(p1: Abstraction<I1>, p2: Abstraction<I2>): Abstraction<O> =
            Application2(function, p1, p2, name)

    override fun toString(): String = name ?: function.toString()

}

fun <I1, I2, O> abstracted(name: String, function: (I1, I2)->O): AbstractFunction2<I1, I2, O> =
        AbstractedFunction2(name, function)

fun <I1, I2, O> ((I1, I2)->O).abstracted(name: String = "f"): AbstractFunction2<I1, I2, O> =
        abstracted(name, this)