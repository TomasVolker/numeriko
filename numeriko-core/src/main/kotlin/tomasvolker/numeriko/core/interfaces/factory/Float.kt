package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

fun FloatArray.asFloatArrayND(): FloatArrayND = floatArrayND(intArrayOf(size), this)
fun FloatArray.toFloatArrayND(): FloatArrayND = floatArrayND(intArrayOf(size), this.copyOf())

fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
        NumerikoConfig.defaultFactory.floatArrayND(shape, data)


fun copy(array: FloatArrayND): FloatArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun floatZeros(shape: IntArrayND): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape)

