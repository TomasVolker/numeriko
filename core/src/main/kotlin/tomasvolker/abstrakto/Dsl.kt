package tomasvolker.abstrakto

import tomasvolker.abstrakto.basic.*

operator fun <I, O> ((I)->O).invoke(input: Abstraction<I>): Abstraction<O> =
        Application1(this, input)

operator fun <I1, I2, O> ((I1, I2)->O).invoke(input1: Abstraction<I1>, input2: Abstraction<I2>): Abstraction<O> =
        Application2(this, input1, input2)

val <T> T.literal: Literal<T> get() = Literal(this)
fun <T> String.variableOf() = Variable<T>(this)

