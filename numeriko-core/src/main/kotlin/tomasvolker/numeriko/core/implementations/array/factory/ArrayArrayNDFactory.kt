package tomasvolker.numeriko.core.implementations.array.factory

import tomasvolker.numeriko.core.implementations.array.arraynd.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class ArrayArrayNDFactory: ArrayNDFactory {

    override fun intArray1D(data: IntArray): IntArray1D =
            ArrayIntArray1D(data)

    override fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
            ArrayGenericArrayND(intArray1D(shape), data)

    override fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
            ArrayDoubleArrayND(intArray1D(shape), data)

    override fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
            ArrayFloatArrayND(intArray1D(shape), data)

    override fun intArrayND(shape: IntArray, data: IntArray): IntArrayND =
            ArrayIntArrayND(intArray1D(shape), data)


}