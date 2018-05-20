package tomasvolker.numeriko.core.jvm.factory

import tomasvolker.numeriko.core.interfaces.factory.ArrayNDFactory
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.jvm.array1d.JvmMutableArray1D

class JvmArrayNDFactory: ArrayNDFactory {

    override fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T> =
            JvmMutableArray1D(data)

    override fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T> =
            mutableArray1DOfNulls<T>(array.size).apply {
                for (i in array.indices) {
                    setValue(array.getValue(i), i)
                }
            } as MutableArray1D<T>

}