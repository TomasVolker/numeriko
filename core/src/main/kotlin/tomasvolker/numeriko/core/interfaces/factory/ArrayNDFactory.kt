@file:Suppress("UNCHECKED_CAST")

package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.MutableIntArray1D
import tomasvolker.numeriko.core.implementations.jvm.factory.JvmArrayNDFactory
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D

var defaultFactory: ArrayNDFactory = JvmArrayNDFactory()

interface ArrayNDFactory {

    fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T>

    fun <T> mutableArray2D(shape0: Int, shape1: Int, data: Array<T>): MutableArray2D<T>

    // Copy

    fun <T> copy(array: Array1D<T>): Array1D<T> =
            mutableCopy(array)

    fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T>

    fun <T> copy(array: Array2D<T>): Array2D<T> =
            mutableCopy(array)

    fun <T> mutableCopy(array: Array2D<T>): MutableArray2D<T>


    fun <T> array1DOfNulls(size: Int): Array1D<T?> =
            mutableArray1DOfNulls(size)

    fun <T> mutableArray1DOfNulls(size: Int): MutableArray1D<T?> =
            mutableArray1D(arrayOfNulls<Any?>(size) as Array<T?>)

    fun <T> array2DOfNulls(shape0: Int, shape1: Int): Array2D<T?> =
            mutableArray2DOfNulls(shape0, shape1)

    fun <T> mutableArray2DOfNulls(shape0: Int, shape1: Int): MutableArray2D<T?> =
            mutableArray2D(shape0, shape1, arrayOfNulls<Any?>(shape0*shape1) as Array<T?>)



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


    fun mutableDoubleArray2D(shape0: Int, shape1: Int, data: DoubleArray): MutableDoubleArray2D

    fun copy(array: DoubleArray2D): DoubleArray2D =
            mutableCopy(array)

    fun mutableCopy(array: DoubleArray2D): MutableDoubleArray2D

    fun doubleZeros(shape0: Int, shape1: Int): DoubleArray2D =
            mutableDoubleZeros(shape0, shape1)

    fun mutableDoubleZeros(shape0: Int, shape1: Int): MutableDoubleArray2D =
            mutableDoubleArray2D(shape0, shape1, DoubleArray(shape0*shape1) { 0.0 })


}