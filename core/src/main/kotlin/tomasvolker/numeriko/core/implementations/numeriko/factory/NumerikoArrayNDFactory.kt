package tomasvolker.numeriko.core.implementations.numeriko.factory

import tomasvolker.numeriko.core.implementations.numeriko.array1d.double.NumerikoMutableDoubleArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array1d.generic.NumerikoMutableArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array1d.integer.NumerikoMutableIntArray1D
import tomasvolker.numeriko.core.implementations.numeriko.array2d.double.NumerikoMutableDoubleArray2D
import tomasvolker.numeriko.core.implementations.numeriko.array2d.generic.NumerikoMutableArray2D
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoMutableArrayND
import tomasvolker.numeriko.core.implementations.numeriko.arraynd.NumerikoMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class NumerikoArrayNDFactory: ArrayNDFactory {

    override fun <T> array1D(data: Array<T>): Array1D<T> =
            NumerikoMutableArray1D(data)

    override fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> =
            NumerikoMutableArray2D(shape0, shape1, data)

    override fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
            NumerikoMutableArrayND(shape, data)

    override fun intArray1D(data: IntArray): IntArray1D =
            NumerikoMutableIntArray1D(data)

    override fun doubleArray1D(data: DoubleArray): DoubleArray1D =
            NumerikoMutableDoubleArray1D(data)

    override fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D =
            NumerikoMutableDoubleArray2D(shape0, shape1, data)

    override fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
            NumerikoMutableDoubleArrayND(shape, data)

}