package tomasvolker.numeriko.core.functional.double

interface DifferentiableFunction: Function {

    override fun invoke(input: Function) = when(input) {
        is ConstantFunction -> invoke(input)
        is DifferentiableFunction -> invoke(input)
        else -> compose(this, input)
    }

    operator fun invoke(input: DifferentiableFunction): DifferentiableFunction =
            composeDifferentiable(this, input)

    fun differentiate(): DifferentiableFunction

    fun derivativeAt(input: Double): Double = differentiate()(input)

    override operator fun unaryPlus(): DifferentiableFunction = this

    override operator fun unaryMinus(): DifferentiableFunction = NegativeFunction(this)

    operator fun plus(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableAddition(this, other)

    operator fun minus(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableAddition(this, -other)

    operator fun times(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableMultiplication(this, other)

    operator fun div(other: DifferentiableFunction): DifferentiableFunction =
            DifferentiableDivision(this, other)

    override operator fun plus(other: Double): DifferentiableFunction =
            this + other.asConstantFunction()

    override operator fun minus(other: Double): DifferentiableFunction =
            this - other.asConstantFunction()

    override operator fun times(other: Double): DifferentiableFunction =
            this * other.asConstantFunction()

    override operator fun div(other: Double): DifferentiableFunction =
            this / other.asConstantFunction()

}
