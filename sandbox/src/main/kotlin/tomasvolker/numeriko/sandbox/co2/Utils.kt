package tomasvolker.numeriko.sandbox.co2

import koma.fill
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun linearSpace(min: Double = 0.0, max: Double = 1.0, count: Int = 100) =
    List(count) { i -> min + (max - min) * i.toDouble() / count }

fun linearInterpolator(x1: Double, y1: Double, x2: Double, y2: Double) =
    LinearFunction(
        m = (y2 - y1) / (x2 - x1),
        b = y1 - x1 * (y2 - y1) / (x2 - x1)
    )

data class LinearFunction(
    val m: Double,
    val b: Double
): (Double)->Double {

    override fun invoke(x: Double): Double =
        m * x + b

}

data class Sine(
    val f: Double
): (Double)->Double {

    override fun invoke(x: Double): Double =
        sin(2 * PI * f * x)

}

data class Cosine(
    val f: Double
): (Double)->Double {

    override fun invoke(x: Double): Double =
        cos(2 * PI * f * x)

}

operator fun Iterable<Double>.plus(other: Iterable<Double>) =
    zip(other) { t, o -> t + o }

operator fun Iterable<Double>.minus(other: Iterable<Double>) =
    zip(other) { t, o -> t - o }

operator fun Iterable<Double>.times(other: Iterable<Double>) =
    zip(other) { t, o -> t * o }

operator fun Iterable<Double>.div(other: Iterable<Double>) =
    zip(other) { t, o -> t / o }

operator fun <I, O> ((I)->O).invoke(input: Iterable<I>): List<O> =
    input.map(this)

fun List<Number>.toRowMatrix() =
    fill(1, this.size) { _, col -> this[col].toDouble() }

fun List<Number>.toColMatrix() =
    fill(this.size, 1) { row, _ -> this[row].toDouble() }

