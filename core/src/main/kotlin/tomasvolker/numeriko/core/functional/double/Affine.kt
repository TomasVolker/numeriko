package tomasvolker.numeriko.core.functional.double

interface AffineFunction: DifferentiableFunction {

    val y0 : Double
    val m  : Double

    override operator fun invoke(input: Double) = y0 + m * input

    override operator fun invoke(input: DifferentiableFunction) = when(input) {
        is ConstantFunction -> invoke(input)
        is AffineFunction -> invoke(this)
        else -> DifferentiableComposition(this, input)
    }

    operator fun invoke(input: AffineFunction): AffineFunction =
            affineFunction(
                y0 = this.y0 + m * input.y0,
                m = this.m * input.m
            )

    override fun differentiate(): ConstantFunction =
            constantFunction(m)

    override fun derivativeAt(input: Double) = m

}

class DefaultAffineFunction(
        override val y0 : Double,
        override val m  : Double
): AffineFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$y0 + $m * $input"

}

interface LinearFunction: AffineFunction {

    override val y0 get() = 0.0

    override operator fun invoke(input: AffineFunction) = when(input) {
        is ConstantFunction -> invoke(input)
        is LinearFunction -> invoke(this)
        else -> super.invoke(input)
    }

    operator fun invoke(input: LinearFunction): LinearFunction =
            linearFunction(this.m * input.m)

    override fun invoke(input: Double) = m * input
    override fun differentiate() = m.asConstantFunction()

}

class DefaultLinearFunction(
        override val m  : Double
): LinearFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$m * $input"

}

object IdentityFunction: LinearFunction {

    override val m: Double get() = 1.0

    override fun invoke(input: Double) = input

    override fun differentiate() = OneFunction
    override fun derivativeAt(input: Double) = 1.0

    override fun toString(input: String) = input

}

object NegativeFunction: LinearFunction {

    override val m: Double get() = -1.0

    override fun invoke(input: Double) = -input

    override fun differentiate() = (-1.0).asConstantFunction()
    override fun derivativeAt(input: Double) = -1.0

    override fun toString(input: String) = input

}

interface ConstantFunction: AffineFunction {
    val value: Double

    override val y0 get() = value
    override val m get() = 0.0

    override fun invoke(input: Double) = value
    override fun invoke(input: Function) = this
    override fun differentiate() = ZeroFunction

    override fun derivativeAt(input: Double) = 0.0

}

class DefaultConstantFunction(
        override val value: Double
) : ConstantFunction {

    override fun toString() = toString("x")

    override fun toString(input: String) = "$value"

}

object ZeroFunction: ConstantFunction {
    override val value get() = 0.0
    override fun invoke(input: Function) = ZeroFunction
    override fun toString() = toString("x")
    override fun toString(input: String) = "0"

}

object OneFunction: ConstantFunction {

    override val value get() = 1.0
    override fun invoke(input: Function) = ZeroFunction
    override fun toString() = toString("x")
    override fun toString(input: String) = "1"

}

private typealias Point = Pair<Double, Double>
private val Point.x get() = first
private val Point.y get() = first

fun linearInterpolator(point1: Point, point2: Point) =
        affineFunction(
                y0 = point1.y,
                m = (point2.y - point1.y) / (point2.x - point1.x)
        )
