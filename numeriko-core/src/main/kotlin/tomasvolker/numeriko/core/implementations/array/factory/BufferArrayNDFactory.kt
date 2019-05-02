package tomasvolker.numeriko.core.implementations.array.factory

import tomasvolker.numeriko.core.implementations.array.arraynd.*
import tomasvolker.numeriko.core.implementations.array.buffer.*
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class BufferArrayNDFactory: ArrayNDFactory {

    override fun intArray1D(data: IntArray): IntArray1D =
            BufferIntArray1D(IntArrayBuffer(data))

    override fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
            BufferGenericArrayND(intArray1D(shape), ArrayBuffer(data))

    override fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
            BufferDoubleArrayND(intArray1D(shape), DoubleArrayBuffer(data))

    override fun floatArrayND(shape: IntArray, data: FloatArray): FloatArrayND =
            BufferFloatArrayND(intArray1D(shape), FloatArrayBuffer(data))

    override fun byteArrayND(shape: IntArray, data: ByteArray): ByteArrayND =
            BufferByteArrayND(intArray1D(shape), ByteArrayBuffer(data))

    override fun intArrayND(shape: IntArray, data: IntArray): IntArrayND =
            BufferIntArrayND(intArray1D(shape), IntArrayBuffer(data))


}