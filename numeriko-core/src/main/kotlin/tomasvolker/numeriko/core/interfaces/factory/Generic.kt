package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.interfaces.arraynd.generic.ArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex

fun <T> Array<T>.asArrayND(
        shape: IntArrayND = I[size]
): ArrayND<T> = arrayND(shape, this)

fun <T> Array<T>.toArrayND(
        shape: IntArrayND = I[size]
): ArrayND<T> = arrayND(shape, this.copyOf())

fun <T> arrayND(shape: IntArray, data: Array<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayND(shape, data)

fun <T> arrayND(shape: IntArrayND, data: Array<T>): ArrayND<T> =
        arrayND(shape.toIntArray(), data)


fun <T> copy(array: ArrayND<T>): ArrayND<T> =
        NumerikoConfig.defaultFactory.copy(array)

fun <T> arrayNDOfNulls(shape: IntArrayND): ArrayND<T?> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls(shape)

inline fun <T> arrayND(shape: IntArrayND, init: (indices: IntArrayND)->T): ArrayND<T> =
        NumerikoConfig.defaultFactory.arrayNDOfNulls<T>(shape).asMutable().apply {
            forEachIndex { indices ->
                setValue(indices, init(indices))
            }
        } as ArrayND<T>

inline fun <T> arrayND(vararg shape: Int, init: (indices: IntArrayND)->T): ArrayND<T> =
        arrayND(shape.asIntArrayND(), init)

