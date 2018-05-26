package tomasvolker.abstrakto.basic

import tomasvolker.abstrakto.common.printToString

sealed class Abstraction<out T> {

    override fun toString(): String = printToString(this)

}

class Literal<T>(
        val value: T
): Abstraction<T>() {

    override fun equals(other: Any?) = when(other) {
        is Literal<*> -> other.value == value
        else -> other == value
    }

    override fun hashCode() = value?.hashCode() ?: 0

}

class Variable<T>(
        val name: String
): Abstraction<T>() {

    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = name.hashCode()

}

class Application1<I, out O>(
        val function: (I)->O,
        val input: Abstraction<I>,
        val functionName: String? = null
): Abstraction<O>()

class Application2<I1, I2, out O>(
        val function: (I1, I2)->O,
        val input1: Abstraction<I1>,
        val input2: Abstraction<I2>,
        val functionName: String? = null
): Abstraction<O>()

