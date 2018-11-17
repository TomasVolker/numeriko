package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun <T> Array<T>.asArray1D(): Array1D<T> = array1D(this)

fun <T> array1DOf(vararg values: T) = array1D(values)


fun <T> array1D(data: Array<T>): Array1D<T> =
        NumerikoConfig.defaultFactory.array1D(data)

fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> =
        NumerikoConfig.defaultFactory.array2D(shape0, shape1, data)

fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayND(shape, data)



fun <T> copy(array: Array1D<T>): Array1D<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> copy(array: Array2D<T>): Array2D<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> copy(array: ArrayND<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.copy(array)



fun <T> array1DOfNulls(size: Int): Array1D<T?> =
        NumerikoConfig.defaultFactory.array1DOfNulls(size)

fun <T> array2DOfNulls(shape0: Int, shape1: Int): Array2D<T?> =
        NumerikoConfig.defaultFactory.array2DOfNulls(shape0, shape1)

fun <T> arrayNDOfNulls(shape: IntArray1D): ArrayND<T?> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls(shape)

