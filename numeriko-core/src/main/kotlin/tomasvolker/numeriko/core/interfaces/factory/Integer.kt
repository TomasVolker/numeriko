package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun IntArray.asIntArrayND(): IntArrayND = intArrayND(intArrayOf(size), this)
fun IntArray.toIntArrayND(): IntArrayND = intArrayND(intArrayOf(size), this.copyOf())

fun intArrayND(shape: IntArray, data: IntArray) =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.intArrayND(shape, data)

fun copy(array: IntArrayND): IntArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.copy(array)


fun intZeros(shape: IntArrayND): IntArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.intZeros(shape)

fun intZeros(vararg shape: Int): IntArrayND =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.intZeros(shape.toIntArrayND())
