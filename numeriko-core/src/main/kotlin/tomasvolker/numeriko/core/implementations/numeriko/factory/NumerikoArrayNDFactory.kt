package tomasvolker.numeriko.core.implementations.numeriko.factory

import tomasvolker.numeriko.core.implementations.numeriko.arraynd.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class NumerikoArrayNDFactory: ArrayNDFactory {

    override fun intArray1D(data: IntArray): IntArray1D =
            NumerikoIntArray1D(data)

    override fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
            NumerikoArrayND(intArray1D(shape), data)

    override fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
            NumerikoDoubleArrayND(intArray1D(shape), data)

    override fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
            NumerikoFloatArrayND(intArray1D(shape), data)

    override fun intArrayND(shape: IntArray, data: IntArray): IntArrayND =
            NumerikoIntArrayND(intArray1D(shape), data)


}