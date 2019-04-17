package tomasvolker.numeriko.core.implementations.numeriko.factory

import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoArrayND
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoDoubleArrayND
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoIntArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class NumerikoArrayNDFactory: ArrayNDFactory {

    override fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
            NumerikoArrayND(intArrayND(intArrayOf(shape.size), shape), data)

    override fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
            NumerikoDoubleArrayND(intArrayND(intArrayOf(shape.size), shape), data)

    override fun intArrayND(shape: IntArray, data: IntArray): IntArrayND =
            NumerikoIntArrayND(shape, data)


}