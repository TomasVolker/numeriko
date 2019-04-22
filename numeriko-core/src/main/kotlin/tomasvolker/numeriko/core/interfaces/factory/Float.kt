package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun FloatArray.asFloatArrayND(
        shape: IntArray1D = I[size]
): FloatArrayND = floatArrayND(shape, this)

fun FloatArray.toFloatArrayND(
        shape: IntArray1D = I[size]
): FloatArrayND = floatArrayND(shape, this.copyOf())

fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
        NumerikoConfig.defaultFactory.floatArrayND(shape, data)

fun floatArrayND(shape: IntArray1D, data: FloatArray): FloatArrayND =
        floatArrayND(shape.toIntArray(), data)


fun copy(array: FloatArrayND): FloatArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun floatZeros(shape: IntArray1D): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape)

fun floatFill(shape: IntArray1D, value: Float): FloatArrayND =
        floatArrayND(shape, FloatArray(shape.product()) { value })

inline fun floatArrayND(vararg shape: Int, init: (indices: IntArray1D)->Number): FloatArrayND =
        floatArrayND(shape.asIntArray1D(), init)

inline fun floatArrayND(shape: IntArray1D, init: (indices: IntArray1D)->Number): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setFloat(indices, init(indices).toFloat())
            }
        }

inline fun unsafeFloatArrayND(shape: IntArray1D, init: (indices: IntArray)->Float): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setFloat(indices, init(indices))
            }
        }
