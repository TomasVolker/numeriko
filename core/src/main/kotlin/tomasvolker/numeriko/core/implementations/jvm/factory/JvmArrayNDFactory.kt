package tomasvolker.numeriko.core.implementations.jvm.factory

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.generic.JvmMutableArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.double.JvmMutableDoubleArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.integer.JvmMutableIntArray1D
import tomasvolker.numeriko.core.implementations.jvm.array2d.double.JvmMutableDoubleArray2D
import tomasvolker.numeriko.core.implementations.jvm.array2d.generic.JvmMutableArray2D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray2D

class JvmArrayNDFactory: ArrayNDFactory {

    override fun <T> mutableArray2D(shape0: Int, shape1: Int, data: Array<T>): MutableArray2D<T> =
            JvmMutableArray2D(shape0, shape1, data)

    override fun <T> mutableCopy(array: Array2D<T>): MutableArray2D<T> =
        mutableArray2DOfNulls<T>(array.shape0, array.shape1).apply {
            forEachIndex { i0, i1 ->
                setValue(array.getValue(i0, i1), i0, i1)
            }
        } as MutableArray2D<T>


    override fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T> =
            JvmMutableArray1D(data)

    override fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T> =
            mutableArray1DOfNulls<T>(array.size).apply {
                for (i in array.indices) {
                    setValue(array.getValue(i), i)
                }
            } as MutableArray1D<T>

    override fun mutableIntArray1D(data: IntArray): MutableIntArray1D =
            JvmMutableIntArray1D(data)

    override fun mutableCopy(array: IntArray1D): MutableIntArray1D =
            mutableIntZeros(array.size).apply {
                for (i in array.indices) {
                    setInt(array.getInt(i), i)
                }
            }

    override fun mutableDoubleArray1D(data: DoubleArray): MutableDoubleArray1D =
            JvmMutableDoubleArray1D(data)

    override fun mutableCopy(array: DoubleArray1D): MutableDoubleArray1D =
            mutableDoubleZeros(array.size).apply {
                for (i in array.indices) {
                    setDouble(array.getDouble(i), i)
                }
            }

    override fun mutableDoubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): MutableDoubleArray2D =
            JvmMutableDoubleArray2D(
                    shape0 = shape0,
                    shape1 = shape1,
                    data = data
            )

    override fun mutableCopy(array: DoubleArray2D): MutableDoubleArray2D =
           mutableDoubleArray2D(array.shape0, array.shape1) { i0, i1 -> array[i0, i1] }

}