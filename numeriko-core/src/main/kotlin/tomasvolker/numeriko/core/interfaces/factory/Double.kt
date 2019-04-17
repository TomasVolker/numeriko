package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun DoubleArray.asDoubleArrayND(): DoubleArrayND = doubleArrayND(intArrayOf(size), this)
fun DoubleArray.toDoubleArrayND(): DoubleArrayND = doubleArrayND(intArrayOf(size), this.copyOf())

fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.doubleArrayND(shape, data)


fun copy(array: DoubleArrayND): DoubleArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.copy(array)

fun doubleZeros(shape: IntArrayND): DoubleArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.doubleZeros(shape)

