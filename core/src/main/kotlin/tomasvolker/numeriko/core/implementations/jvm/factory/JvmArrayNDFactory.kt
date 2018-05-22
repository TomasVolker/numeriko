package tomasvolker.numeriko.core.implementations.jvm.factory

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.generic.JvmMutableArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.integer.JvmMutableDoubleArray1D
import tomasvolker.numeriko.core.implementations.jvm.array1d.integer.JvmMutableIntArray1D

class JvmArrayNDFactory: ArrayNDFactory {

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

}