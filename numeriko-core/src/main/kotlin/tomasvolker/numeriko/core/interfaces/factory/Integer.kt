package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import kotlin.random.Random

fun IntArray.asIntArray1D(): IntArray1D = intArray1D(this)

fun IntArray.toIntArray1D(): IntArray1D = intArray1D(this.copyOf())

fun intArray1DOf(vararg values: Int) = intArray1D(values)


fun intArray1D(data: IntArray): IntArray1D =
        NumerikoConfig.defaultFactory.intArray1D(data)


fun copy(array: IntArray1D): IntArray1D =
        NumerikoConfig.defaultFactory.copy(array)


fun intZeros(size: Int): IntArray1D =
        NumerikoConfig.defaultFactory.intZeros(size)

fun Random.nextIntArray1D(size: Int): IntArray1D = intArray1D(size) { nextInt() }
fun Random.nextIntArray1D(size: Int, until: Int): IntArray1D = intArray1D(size) { nextInt(until) }
fun Random.nextIntArray1D(size: Int, from: Int, until: Int): IntArray1D = intArray1D(size) { nextInt(from, until) }
