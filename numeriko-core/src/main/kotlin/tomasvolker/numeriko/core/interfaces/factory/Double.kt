package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.float.FloatArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun DoubleArray.asDoubleArrayND(
        shape: IntArrayND = I[size]
): DoubleArrayND = doubleArrayND(shape, this)

fun DoubleArray.toDoubleArrayND(
        shape: IntArrayND = I[size]
): DoubleArrayND = doubleArrayND(shape, this.copyOf())

fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleArrayND(shape, data)

fun doubleArrayND(shape: IntArrayND, data: DoubleArray): DoubleArrayND =
        doubleArrayND(shape.toIntArray(), data)


fun copy(array: DoubleArrayND): DoubleArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun doubleZeros(shape: IntArrayND): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape)

fun doubleFill(shape: IntArrayND, value: Double): DoubleArrayND =
        doubleArrayND(shape, DoubleArray(shape.product()) { value })

inline fun doubleArrayND(vararg shape: Int, init: (indices: IntArrayND)->Number): DoubleArrayND =
        doubleArrayND(shape.asIntArrayND(), init)

inline fun doubleArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Number): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setDouble(indices, init(indices).toDouble())
            }
        }

inline fun unsafeDoubleArrayND(shape: IntArrayND, init: (indices: IntArray)->Double): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setDouble(indices, init(indices))
            }
        }
