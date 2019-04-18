@file:Suppress("UNCHECKED_CAST")

package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.fastForEachIndices
import tomasvolker.numeriko.core.operations.reduction.product

interface ArrayNDFactory {

    fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T>

    fun <T> copy(array: ArrayND<T>): ArrayND<T> =
            arrayND(array.shape) { indices -> array.getValue(indices) }

    fun <T> arrayNDOfNulls(shape: IntArrayND): ArrayND<T?> =
            arrayND(shape.toIntArray(), arrayOfNulls<Any?>(shape.product()) as Array<T?>)

    // Double

    fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND

    fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND

    fun intArrayND(shape: IntArray, data: IntArray): IntArrayND

    // Copy

    fun copy(array: DoubleArrayND): DoubleArrayND =
            fastDoubleArrayND(array.shape) { indices -> array.getDouble(indices) }

    fun copy(array: FloatArrayND): FloatArrayND =
            fastFloatArrayND(array.shape) { indices -> array.getFloat(indices) }

    fun copy(array: IntArrayND): IntArrayND =
            intArrayND(array.shape) { indices -> array.getInt(indices) }

    // Zeros

    fun doubleZeros(shape: IntArrayND): DoubleArrayND =
            doubleArrayND(shape.toIntArray(), DoubleArray(shape.product()) { 0.0 })

    fun floatZeros(shape: IntArrayND): FloatArrayND =
            floatArrayND(shape.toIntArray(), FloatArray(shape.product()) { 0.0f })

    fun intZeros(shape: IntArrayND): IntArrayND =
            intArrayND(shape.toIntArray(), IntArray(shape.product()) { 0 })

}

inline fun <T> arrayND(shape: IntArrayND, init: (indices: IntArrayND)->T): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls<T>(shape).asMutable().apply {
            forEachIndices { indices ->
                setValue(indices, init(indices))
            }
        } as ArrayND<T>

inline fun <T> arrayND(vararg shape: Int, init: (indices: IntArrayND)->T): ArrayND<T> =
        arrayND(shape.asIntArrayND(), init)

inline fun doubleArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Number): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            forEachIndices { indices ->
                this.setDouble(indices, init(indices).toDouble())
            }
        }

inline fun floatArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Number): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            forEachIndices { indices ->
                this.setFloat(indices, init(indices).toFloat())
            }
        }

inline fun intArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape).asMutable().apply {
            forEachIndices { indices ->
                this.setInt(indices, init(indices))
            }
        }

inline fun fastDoubleArrayND(shape: IntArrayND, init: (indices: IntArray)->Double): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            fastForEachIndices { indices ->
                this.setDouble(indices, init(indices))
            }
        }

inline fun fastFloatArrayND(shape: IntArrayND, init: (indices: IntArray)->Float): FloatArrayND =
        NumerikoConfig.defaultFactory.floatZeros(shape).asMutable().apply {
            fastForEachIndices { indices ->
                this.setFloat(indices, init(indices))
            }
        }

inline fun doubleArrayND(vararg shape: Int, init: (indices: IntArrayND)->Number): DoubleArrayND =
        doubleArrayND(shape.asIntArrayND(), init)

inline fun floatArrayND(vararg shape: Int, init: (indices: IntArrayND)->Number): FloatArrayND =
        floatArrayND(shape.asIntArrayND(), init)

inline fun intArrayND(vararg shape: Int, init: (indices: IntArrayND)->Int): IntArrayND =
        intArrayND(shape.asIntArrayND(), init)
