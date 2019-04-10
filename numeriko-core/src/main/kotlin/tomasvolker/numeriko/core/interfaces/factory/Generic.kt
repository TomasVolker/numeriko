package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.lowrank.interfaces.array0d.generic.Array0D
import tomasvolker.numeriko.core.interfaces.array1d.generic.Array1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.lowrank.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND

fun <T> Array<T>.asArray1D(): Array1D<T> = array1D(this)

fun <T> array1DOf(vararg values: T) = array1D(values)

fun <T> array0DOf(value: T): Array0D<T> =
        array0D(value)

fun <T> array0D(value: T): Array0D<T> =
        NumerikoConfig.defaultFactory.array0D(value)

fun <T> array1D(data: Array<T>): Array1D<T> =
        NumerikoConfig.defaultFactory.array1D(data)

fun <T> array2D(shape0: Int, shape1: Int, data: Array<T>): Array2D<T> =
        NumerikoConfig.defaultFactory.array2D(shape0, shape1, data)

fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayND(shape, data)


fun <T> copy(array: Array0D<T>): Array0D<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> copy(array: Array1D<T>): Array1D<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> copy(array: Array2D<T>): Array2D<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> copy(array: ArrayND<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.copy(array)


fun <T> array0DOfNull(): Array0D<T?> =
        NumerikoConfig.defaultFactory.array0DOfNull()

fun <T> array1DOfNulls(size: Int): Array1D<T?> =
        NumerikoConfig.defaultFactory.array1DOfNulls(size)

fun <T> array2DOfNulls(shape0: Int, shape1: Int): Array2D<T?> =
        NumerikoConfig.defaultFactory.array2DOfNulls(shape0, shape1)

fun <T> arrayNDOfNulls(shape: IntArray1D): ArrayND<T?> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls(shape)

