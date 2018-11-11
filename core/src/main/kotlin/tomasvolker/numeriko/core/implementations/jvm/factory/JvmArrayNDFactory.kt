package tomasvolker.numeriko.core.implementations.jvm.factory

import tomasvolker.numeriko.core.implementations.jvm.array1d.double.JvmMutableDoubleArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.generic.JvmMutableArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.integer.JvmMutableIntArray1D
import tomasvolker.numeriko.core.implementations.jvm.array2d.double.JvmMutableDoubleArray2D
import tomasvolker.numeriko.core.implementations.jvm.array2d.generic.JvmMutableArray2D
import tomasvolker.numeriko.core.implementations.jvm.arraynd.JvmMutableArrayND
import tomasvolker.numeriko.core.implementations.jvm.arraynd.JvmMutableDoubleArrayND
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory


class JvmArrayNDFactory: ArrayNDFactory {

    override fun <T> array1D(data: Array<T>): Array1D<T> =
            JvmMutableArray1D(data)

    override fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> =
            JvmMutableArray2D(shape0, shape1, data)

    override fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
            JvmMutableArrayND(shape, data)

    override fun intArray1D(data: IntArray): IntArray1D =
            JvmMutableIntArray1D(data)

    override fun doubleArray1D(data: DoubleArray): DoubleArray1D =
            JvmMutableDoubleArray1D(data)

    override fun doubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): DoubleArray2D =
            JvmMutableDoubleArray2D(shape0, shape1, data)

    override fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
            JvmMutableDoubleArrayND(shape, data)

}