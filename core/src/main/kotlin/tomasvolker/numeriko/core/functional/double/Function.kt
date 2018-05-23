package tomasvolker.numeriko.core.functional.double

interface Function {

    operator fun invoke(input: Double): Double

    operator fun invoke(input: Function): Function = when(input) {
        is ConstantFunction -> invoke(input)
        else -> compose(this, input)
    }

    operator fun invoke(input: ConstantFunction) =
            constantFunction(this(input.value))

    fun toLambda(): (Double)->Double = { invoke(it) }

    operator fun unaryPlus() = this

    operator fun unaryMinus(): Function = function { -it }

    operator fun plus(other: Function) = function { it + other(it) }

    operator fun minus(other: Function) = function { it - other(it) }

    operator fun times(other: Function) = function { it * other(it) }

    operator fun div(other: Function) = function { it / other(it) }

    operator fun plus(other: Double) = function { it + other }

    operator fun minus(other: Double) = function { it - other }

    operator fun times(other: Double) = function { it * other }

    operator fun div(other: Double) = function { it / other }

    fun toString(input: String): String

}
