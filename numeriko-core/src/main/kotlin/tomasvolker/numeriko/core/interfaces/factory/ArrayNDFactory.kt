@file:Suppress("UNCHECKED_CAST")

package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
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
            unsafeDoubleArrayND(array.shape) { indices -> array.getDouble(indices) }

    fun copy(array: FloatArrayND): FloatArrayND =
            unsafeFloatArrayND(array.shape) { indices -> array.getFloat(indices) }

    fun copy(array: IntArrayND): IntArrayND =
            unsafeIntArrayND(array.shape) { indices -> array.getInt(indices) }

    // Zeros

    fun doubleZeros(shape: IntArrayND): DoubleArrayND =
            doubleArrayND(shape.toIntArray(), DoubleArray(shape.product()) { 0.0 })

    fun floatZeros(shape: IntArrayND): FloatArrayND =
            floatArrayND(shape.toIntArray(), FloatArray(shape.product()) { 0.0f })

    fun intZeros(shape: IntArrayND): IntArrayND =
            intArrayND(shape.toIntArray(), IntArray(shape.product()) { 0 })

}


