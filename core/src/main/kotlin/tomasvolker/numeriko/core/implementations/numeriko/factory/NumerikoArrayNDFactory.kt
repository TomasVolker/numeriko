package tomasvolker.numeriko.core.implementations.numeriko.factory

import tomasvolker.numeriko.core.implementations.numeriko.array0d.double.NumerikoDoubleArray0D
import tomasvolker.numeriko.core.implementations.numeriko.array0d.generic.NumerikoArray0D
import tomasvolker.numeriko.core.implementations.numeriko.array1d.double.NumerikoDoubleArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array1d.generic.NumerikoArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array1d.integer.NumerikoIntArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array2d.double.NumerikoDoubleArray2D
import tomasvolker.numeriko.core.implementations.numeriko.array2d.generic.NumerikoArray2D
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoArrayND
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoDoubleArrayND
import tomasvolker.numeriko.core.interfaces.array0d.double.DoubleArray0D
import tomasvolker.numeriko.core.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class NumerikoArrayNDFactory: ArrayNDFactory {

    override fun <T> array0D(value: T): Array0D<T> =
            NumerikoArray0D(value)

    override fun <T> array1D(data: Array<T>): Array1D<T> =
            NumerikoArray1D(data)

    override fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> =
            NumerikoArray2D(shape0, shape1, data)

    override fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
            NumerikoArrayND(shape, data)

    override fun intArray1D(data: IntArray): IntArray1D =
            NumerikoIntArray1D(data)

    override fun doubleArray0D(value: Double): DoubleArray0D =
            NumerikoDoubleArray0D(value)

    override fun doubleArray1D(data: DoubleArray): DoubleArray1D =
            NumerikoDoubleArray1D(data)

    override fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D =
            NumerikoDoubleArray2D(shape0, shape1, data)

    override fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
            NumerikoDoubleArrayND(shape, data)

}