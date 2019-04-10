package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.lowrank.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.probability.continuous.NormalDistribution
import kotlin.math.*
import kotlin.random.Random

fun DoubleArray.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this)
fun DoubleArray.toDoubleArray1D(): DoubleArray1D = doubleArray1D(this.copyOf())

fun Array<out Number>.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this.map { it.toDouble() }.toDoubleArray())

fun Iterable<Number>.toDoubleArray1D(): DoubleArray1D = doubleArray1D(this.map { it.toDouble() }.toDoubleArray())

fun doubleArray1DOf(vararg values: Double) = doubleArray1D(values)
fun doubleArray1DOf(value: Double) = doubleArray0D(value)


fun doubleArray0D(value: Double): DoubleArray0D =
        NumerikoConfig.defaultFactory.doubleArray0D(value)

fun doubleArray1D(value: DoubleArray): DoubleArray1D =
        NumerikoConfig.defaultFactory.doubleArray1D(value)

fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleArray2D(shape0, shape1, data)

fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleArrayND(shape, data)


fun copy(array: DoubleArray0D): DoubleArray0D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: DoubleArray1D): DoubleArray1D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: DoubleArray2D): DoubleArray2D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: DoubleArrayND): DoubleArrayND =
        NumerikoConfig.defaultFactory.copy(array)


fun doubleZeros(): DoubleArray0D =
        NumerikoConfig.defaultFactory.doubleZeros()

fun doubleZeros(size: Int): DoubleArray1D =
        NumerikoConfig.defaultFactory.doubleZeros(size)

fun doubleZeros(shape0: Int, shape1: Int): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleZeros(shape0, shape1)

fun doubleZeros(shape: IntArray1D): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape)


fun doubleIdentity(size: Int): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleIdentity(size)


fun doubleRandom(size: Int): DoubleArray1D =
        NumerikoConfig.defaultFactory.doubleRandom(size)

fun doubleRandom(shape0: Int, shape1: Int): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleRandom(shape0, shape1)

fun doubleRandom(shape: IntArray1D): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleRandom(shape)


inline fun doubleDiagonal(size: Int, diagonal: (i: Int)->Double): DoubleArray2D =
        doubleArray2D(size, size) { i0, i1 ->
            if (i0 == i1)
                diagonal(i0)
            else
                0.0
        }

fun doubleDiagonal(diagonal: DoubleArray1D): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleDiagonal(diagonal)


fun Random.nextDoubleArray0D(): DoubleArray0D = doubleArray0D(nextDouble())
fun Random.nextDoubleArray0D(from: Double, until: Double): DoubleArray0D =
        doubleArray0D(nextDouble(from, until))

fun Random.nextDoubleArray1D(size: Int): DoubleArray1D = doubleArray1D(size) { nextDouble() }
fun Random.nextDoubleArray1D(size: Int, from: Double, until: Double): DoubleArray1D =
        doubleArray1D(size) { nextDouble(from, until) }

fun Random.nextDoubleArray2D(shape0: Int, shape1: Int): DoubleArray2D =
        doubleArray2D(shape0, shape1) { _, _ -> nextDouble() }
fun Random.nextDoubleArray2D(shape0: Int, shape1: Int, from: Double, until: Double): DoubleArray2D =
        doubleArray2D(shape0, shape1) { _, _ -> nextDouble(from, until) }

fun Random.nextDoubleArrayND(shape: IntArray1D): DoubleArrayND = fastDoubleArrayND(shape) { nextDouble() }
fun Random.nextDoubleArrayND(shape: IntArray1D, from: Double, until: Double): DoubleArrayND =
        doubleArrayND(shape) { nextDouble(from, until) }

fun Random.nextGaussian(): Double {

    // Box muller
    val angle = nextDouble(2 * PI)
    val abs = sqrt(-2 * ln(nextDouble()))

    return abs * cos(angle)
}

fun Random.nextGaussianArray1D(size: Int, mean: Double = 0.0, deviation: Double = 1.0): DoubleArray1D {

    val normal = NormalDistribution(
            mean = mean,
            deviation = deviation
    )

    return doubleArray1D(size) { normal.nextDouble(this) }
}

fun Random.nextGaussianArray2D(shape0: Int, shape1: Int, mean: Double = 0.0, deviation: Double = 1.0): DoubleArray2D {

    val normal = NormalDistribution(
            mean = mean,
            deviation = deviation
    )

    return doubleArray2D(shape0, shape1) { _, _ -> normal.nextDouble(this) }
}

fun Random.nextGaussianArrayND(shape: IntArray1D, mean: Double = 0.0, deviation: Double = 1.0): DoubleArrayND {

    val normal = NormalDistribution(
            mean = mean,
            deviation = deviation
    )

    return doubleArrayND(shape) { normal.nextDouble(this) }
}
