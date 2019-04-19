package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun <T> Array<T>.asArrayND(
        shape: IntArray1D = I[size]
): ArrayND<T> = arrayND(shape, this)

fun <T> Array<T>.toArrayND(
        shape: IntArray1D = I[size]
): ArrayND<T> = arrayND(shape, this.copyOf())

fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayND(shape, data)

fun <T> arrayND(shape: IntArray1D, data: Array<T>): ArrayND<T> =
        arrayND(shape.toIntArray(), data)


fun <T> copy(array: ArrayND<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> arrayNDOfNulls(shape: IntArray1D): ArrayND<T?> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls(shape)

inline fun <reified T> arrayFill(shape: IntArray1D, value: T): ArrayND<T> =
        arrayND(shape, Array(shape.product()) { value })

inline fun <T> arrayND(shape: IntArray1D, init: (indices: IntArray1D)->T): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls<T>(shape).asMutable().apply {
            forEachIndex { indices ->
                setValue(indices, init(indices))
            }
        } as ArrayND<T>

inline fun <T> arrayND(vararg shape: Int, init: (indices: IntArray1D)->T): ArrayND<T> =
        arrayND(shape.asIntArray1D(), init)

inline fun <T> unsafeArrayND(shape: IntArray1D, init: (indices: IntArray)->T): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls<T>(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setValue(indices, init(indices))
            }
        } as ArrayND<T>
