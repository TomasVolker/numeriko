package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import kotlin.random.Random

fun DoubleArray.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this)
fun DoubleArray.toDoubleArray1D(): DoubleArray1D = doubleArray1D(this.copyOf())

fun Array<out Number>.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this.map { it.toDouble() }.toDoubleArray())

fun doubleArray1DOf(vararg values: Double) = doubleArray1D(values)



fun doubleArray1D(data: DoubleArray): DoubleArray1D =
        NumerikoConfig.defaultFactory.doubleArray1D(data)

fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D =
        NumerikoConfig.defaultFactory.doubleArray2D(shape0, shape1, data)

fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleArrayND(shape, data)



fun copy(array: DoubleArray1D): DoubleArray1D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: DoubleArray2D): DoubleArray2D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: DoubleArrayND): DoubleArrayND =
        NumerikoConfig.defaultFactory.copy(array)



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

