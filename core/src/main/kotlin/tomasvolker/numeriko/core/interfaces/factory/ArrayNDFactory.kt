package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.IntArray1D
import tomasvolker.numeriko.core.interfaces.integer.array1d.MutableIntArray1D
import tomasvolker.numeriko.core.jvm.factory.JvmArrayNDFactory

var defaultFactory: ArrayNDFactory = JvmArrayNDFactory()

interface ArrayNDFactory {

    fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T>

    fun <T> copy(array: Array1D<T>): Array1D<T> =
            mutableCopy(array)

    fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T>


    fun <T> array1DOfNulls(size: Int): Array1D<T?> =
            mutableArray1DOfNulls(size)

    fun <T> mutableArray1DOfNulls(size: Int): MutableArray1D<T?> =
            mutableArray1D(arrayOfNulls<Any?>(size) as Array<T?>)

    fun mutableIntArray1D(data: IntArray): MutableIntArray1D

    fun copy(array: IntArray1D): IntArray1D =
            mutableCopy(array)

    fun mutableCopy(array: IntArray1D): MutableIntArray1D

    fun intZeros(size: Int): IntArray1D =
            mutableIntZeros(size)

    fun mutableIntZeros(size: Int): MutableIntArray1D =
            mutableIntArray1D(IntArray(size) { 0 })


    fun mutableDoubleArray1D(data: DoubleArray): MutableDoubleArray1D

    fun copy(array: DoubleArray1D): DoubleArray1D =
            mutableCopy(array)

    fun mutableCopy(array: DoubleArray1D): MutableDoubleArray1D

    fun doubleZeros(size: Int): DoubleArray1D =
            mutableDoubleZeros(size)

    fun mutableDoubleZeros(size: Int): MutableDoubleArray1D =
            mutableDoubleArray1D(DoubleArray(size) { 0.0 })

}