package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.generic.array1d.Array1D
import tomasvolker.numeriko.core.interfaces.generic.array1d.MutableArray1D
import tomasvolker.numeriko.core.jvm.factory.JvmArrayNDFactory

var defaultFactory: ArrayNDFactory = JvmArrayNDFactory()

fun <T> Array<T>.asArray1D(): Array1D<T> = array1D(this)

fun <T> Array<T>.asMutableArray1D(): Array1D<T> = mutableArray1D(this)

fun <T> array1D(data: Array<T>): Array1D<T> = mutableArray1D(data)

inline fun <reified T> array1D(size: Int, init: (index: Int)->T): Array1D<T> =
        mutableArray1D(size, init)

fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T> =
        defaultFactory.mutableArray1D(data)

inline fun <reified T> mutableArray1D(size: Int, init: (index: Int)->T): MutableArray1D<T> =
        mutableArray1D(Array<T?>(size) {i -> init(i) } as Array<T>)

fun <T> array1DOf(vararg values: T) = array1D(values)

fun <T> mutableArray1DOf(vararg values: T) = mutableArray1D(values)

fun <T> copy(array: Array1D<T>): Array1D<T> =
        defaultFactory.copy(array)

fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T> =
        defaultFactory.mutableCopy(array)

interface ArrayNDFactory {

    fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T>

    fun <T> copy(array: Array1D<T>): Array1D<T> =
            mutableCopy(array)

    fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T>


    fun <T> array1DOfNulls(size: Int): Array1D<T?> =
            mutableArray1DOfNulls(size)

    fun <T> mutableArray1DOfNulls(size: Int): MutableArray1D<T?> =
            mutableArray1D(arrayOfNulls<Any?>(size) as Array<T?>)

}