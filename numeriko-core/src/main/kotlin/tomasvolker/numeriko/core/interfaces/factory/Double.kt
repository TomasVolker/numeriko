package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun DoubleArray.asDoubleArrayND(
        shape: IntArray1D = I[size]
): DoubleArrayND = doubleArrayND(shape, this)

fun DoubleArray.toDoubleArrayND(
        shape: IntArray1D = I[size]
): DoubleArrayND = doubleArrayND(shape, this.copyOf())

fun doubleArrayND(shape: IntArray, data: DoubleArray): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleArrayND(shape, data)

fun doubleArrayND(shape: IntArray1D, data: DoubleArray): DoubleArrayND =
        doubleArrayND(shape.toIntArray(), data)


fun copy(array: DoubleArrayND): DoubleArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun doubleZeros(shape: IntArray1D): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape)

fun doubleFill(shape: IntArray1D, value: Double): DoubleArrayND =
        doubleArrayND(shape, DoubleArray(shape.product()) { value })

inline fun doubleArrayND(vararg shape: Int, init: (indices: IntArray1D)->Number): DoubleArrayND =
        doubleArrayND(shape.asIntArray1D(), init)

inline fun doubleArrayND(shape: IntArray1D, init: (indices: IntArray1D)->Number): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setDouble(indices, init(indices).toDouble())
            }
        }

inline fun unsafeDoubleArrayND(shape: IntArray1D, init: (indices: IntArray)->Double): DoubleArrayND =
        NumerikoConfig.defaultFactory.doubleZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setDouble(indices, init(indices))
            }
        }
