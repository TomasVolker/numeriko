package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun <T> Array<T>.asArrayND(): ArrayND<T> = arrayND(intArrayOf(size), this)
fun <T> Array<T>.toArrayND(): ArrayND<T> = arrayND(intArrayOf(size), this.copyOf())

fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.arrayND(shape, data)

fun <T> copy(array: ArrayND<T>): ArrayND<T> =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.copy(array)

fun <T> arrayNDOfNulls(shape: IntArrayND): ArrayND<T?> =
        tomasvolker.numeriko.core.config.NumerikoConfig.defaultFactory.arrayNDOfNulls(shape)

