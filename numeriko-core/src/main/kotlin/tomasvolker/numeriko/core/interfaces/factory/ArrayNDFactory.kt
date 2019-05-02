@file:Suppress("UNCHECKED_CAST")

package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND

interface ArrayNDFactory {

    fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T>

    fun <T> copy(array: ArrayND<T>): ArrayND<T> =
            arrayND(array.shape) { indices -> array.getValue(indices) }

    fun <T> arrayNDOfNulls(shape: IntArrayND): ArrayND<T?> =
            arrayND(shape.toIntArray(), arrayOfNulls<Any?>(shape.product()) as Array<T?>)

    fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND

    fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND

    fun intArrayND(shape: IntArray, data: IntArray): IntArrayND

    fun byteArrayND(shape: IntArray, data: ByteArray): ByteArrayND

    fun intArray1D(data: IntArray): IntArray1D

    // Copy

    fun copy(array: IntArray1D): IntArray1D =
            intArray1D(array.size) { i -> array[i] }

    fun copy(array: IntArrayND): IntArrayND =
            unsafeIntArrayND(array.shape) { indices -> array.getInt(indices) }

    fun copy(array: ByteArrayND): ByteArrayND =
            unsafeByteArrayND(array.shape) { indices -> array.getByte(indices) }

    fun copy(array: DoubleArrayND): DoubleArrayND =
            unsafeDoubleArrayND(array.shape) { indices -> array.getDouble(indices) }

    fun copy(array: FloatArrayND): FloatArrayND =
            unsafeFloatArrayND(array.shape) { indices -> array.getFloat(indices) }

    // Zeros

    fun doubleZeros(shape: IntArray1D): DoubleArrayND =
            doubleArrayND(shape.toIntArray(), DoubleArray(shape.product()) { 0.0 })

    fun floatZeros(shape: IntArray1D): FloatArrayND =
            floatArrayND(shape.toIntArray(), FloatArray(shape.product()) { 0.0f })

    fun intZeros(shape: IntArray1D): IntArrayND =
            intArrayND(shape.toIntArray(), IntArray(shape.product()) { 0 })

    fun byteZeros(shape: IntArray1D): ByteArrayND =
            byteArrayND(shape.toIntArray(), ByteArray(shape.product()) { 0 })

}


