package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.MutableArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.MutableArray2D

fun <T> Array<T>.asArray1D(): Array1D<T> = array1D(this)

fun <T> Array<T>.asMutableArray1D(): MutableArray1D<T> = mutableArray1D(this)

fun <T> array1D(data: Array<T>): Array1D<T> = mutableArray1D(data)

fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> = mutableArray2D(shape0, shape1, data)

inline fun <reified T> array1D(size: Int, init: (index: Int)->T): Array1D<T> =
        mutableArray1D(size, init)

fun <T> mutableArray1D(data: Array<T>): MutableArray1D<T> =
        defaultFactory.mutableArray1D(data)

fun <T> mutableArray2D(shape0: Int, shape1: Int, data: Array<T>): MutableArray2D<T> =
        defaultFactory.mutableArray2D(shape0, shape1, data)

inline fun <reified T> mutableArray1D(size: Int, init: (index: Int)->T): MutableArray1D<T> =
        mutableArray1D(Array<T?>(size) { i -> init(i) } as Array<T>)

inline fun <reified T> mutableArray2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->T): MutableArray2D<T> =
        mutableArray2D(shape0, shape1, Array(shape0 * shape1) { i -> init(i / shape1, i % shape1) })

inline fun <reified T> array2D(shape0: Int, shape1: Int, init: (i0: Int, i1: Int)->T): Array2D<T> =
        mutableArray2D(shape0, shape1, init)

fun <T> array1DOf(vararg values: T) = array1D(values)

fun <T> mutableArray1DOf(vararg values: T) = mutableArray1D(values)

fun <T> copy(array: Array1D<T>): Array1D<T> =
        defaultFactory.copy(array)

fun <T> copy(array: Array2D<T>): Array2D<T> =
        defaultFactory.copy(array)

fun <T> mutableCopy(array: Array1D<T>): MutableArray1D<T> =
        defaultFactory.mutableCopy(array)

fun <T> mutableCopy(array: Array2D<T>): MutableArray2D<T> =
        defaultFactory.mutableCopy(array)

fun <T> mutableArray1DOfNulls(size: Int): MutableArray1D<T?> =
        defaultFactory.mutableArray1DOfNulls(size)

fun <T> mutableArray2DOfNulls(shape0: Int, shape1: Int): MutableArray2D<T?> =
        defaultFactory.mutableArray2DOfNulls(shape0, shape1)