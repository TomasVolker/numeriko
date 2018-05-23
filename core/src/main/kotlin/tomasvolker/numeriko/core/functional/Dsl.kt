package tomasvolker.numeriko.core.functional

import tomasvolker.numeriko.core.functional.affine.DefaultAffineFunction
import tomasvolker.numeriko.core.functional.affine.linear.*
import tomasvolker.numeriko.core.functional.operators.DifferentiableComposition

fun <L: DifferentiableFunction, R: DifferentiableFunction> composeDifferentiable(function1: L, function2: R) =
        DifferentiableComposition(function1, function2)

fun constant(value: Double): ConstantFunction = DefaultConstantFunction(value)
fun constant(value: Long): IntegerConstantFunction = when(value) {
    0L -> ZeroFunction
    1L -> OneFunction
    -1L -> MinusOneFunction
    else -> DefaultIntegerConstantFunction(value)
}
fun affineFunction(y0: Double, m: Double) = DefaultAffineFunction(y0, m)

private typealias Point = Pair<Double, Double>
private val Point.x get() = first
private val Point.y get() = first

fun linearInterpolator(point1: Point, point2: Point) =
        affineFunction(
                y0 = point1.y,
                m = (point2.y - point1.y) / (point2.x - point1.x)
        )


fun linearFunction(m: Double): LinearFunction = DefaultLinearFunction(m)

fun Double.asConstant() = constant(this)
fun Int.asConstant() = constant(this.toLong())
fun Long.asConstant() = constant(this)
