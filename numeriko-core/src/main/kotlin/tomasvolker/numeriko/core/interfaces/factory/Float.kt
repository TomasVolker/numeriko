package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun FloatArray.asFloatArrayND(
        shape: IntArrayND = I[size]
): FloatArrayND = floatArrayND(shape, this)

fun FloatArray.toFloatArrayND(
        shape: IntArrayND = I[size]
): FloatArrayND = floatArrayND(shape, this.copyOf())

fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
        NumerikoConfig.defaultFactory.floatArrayND(shape, data)

fun floatArrayND(shape: IntArrayND, data: FloatArray): FloatArrayND =
        floatArrayND(shape.toIntArray(), data)


fun copy(array: FloatArrayND): FloatArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun floatZeros(shape: IntArrayND): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape)

inline fun floatArrayND(vararg shape: Int, init: (indices: IntArrayND)->Number): FloatArrayND =
        floatArrayND(shape.asIntArrayND(), init)

inline fun floatArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Number): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setFloat(indices, init(indices).toFloat())
            }
        }

inline fun unsafeFloatArrayND(shape: IntArrayND, init: (indices: IntArray)->Float): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setFloat(indices, init(indices))
            }
        }
