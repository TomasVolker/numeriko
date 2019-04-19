package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex
import tomasvolker.numeriko.core.serialization.rawBytes

fun IntArray.asIntArrayND(): IntArrayND = intArrayND(intArrayOf(size), this)
fun IntArray.toIntArrayND(): IntArrayND = intArrayND(intArrayOf(size), this.copyOf())

fun IntArray.asIntArrayND(
        shape: IntArrayND
): IntArrayND = intArrayND(shape, this)

fun IntArray.toIntArrayND(
        shape: IntArrayND
): IntArrayND = intArrayND(shape, this.copyOf())

fun intArrayND(shape: IntArray, data: IntArray) =
        NumerikoConfig.defaultFactory.intArrayND(shape, data)

fun intArrayND(shape: IntArrayND, data: IntArray) =
        intArrayND(shape.toIntArray(), data)

fun copy(array: IntArrayND): IntArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun intZeros(shape: IntArrayND): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape)

fun intZeros(vararg shape: Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape.toIntArrayND())

fun intFill(shape: IntArrayND, value: Int): IntArrayND =
        intArrayND(shape, IntArray(shape.product()) { value })

inline fun intArrayND(vararg shape: Int, init: (indices: IntArrayND)->Int): IntArrayND =
        intArrayND(shape.asIntArrayND(), init)

inline fun intArrayND(shape: IntArrayND, init: (indices: IntArrayND)->Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setInt(indices, init(indices))
            }
        }

inline fun unsafeIntArrayND(shape: IntArrayND, init: (indices: IntArray)->Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setInt(indices, init(indices))
            }
        }
