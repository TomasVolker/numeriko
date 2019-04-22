package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.integer.IntArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun IntArray.asIntArray1D(): IntArray1D = intArray1D(this)
fun IntArray.toIntArray1D(): IntArray1D = intArray1D(this.copyOf())

fun IntArray.asIntArrayND(
        shape: IntArray1D
): IntArrayND = intArrayND(shape, this)

fun IntArray.toIntArrayND(
        shape: IntArray1D
): IntArrayND = intArrayND(shape, this.copyOf())

fun intArray1D(data: IntArray) =
        NumerikoConfig.defaultFactory.intArray1D(data)

fun intArrayND(shape: IntArray, data: IntArray) =
        NumerikoConfig.defaultFactory.intArrayND(shape, data)

fun intArrayND(shape: IntArray1D, data: IntArray) =
        intArrayND(shape.toIntArray(), data)

fun copy(array: IntArray1D): IntArray1D =
        NumerikoConfig.defaultFactory.copy(array)

fun copy(array: IntArrayND): IntArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun intZeros(shape: IntArray1D): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape)

fun intZeros(size: Int): IntArray1D =
        NumerikoConfig.defaultFactory.intArray1D(IntArray(size) { 0 })

fun intZeros(vararg shape: Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape.toIntArray1D())

fun intFill(shape: IntArray1D, value: Int): IntArrayND =
        intArrayND(shape, IntArray(shape.product()) { value })

inline fun intArrayND(vararg shape: Int, init: (indices: IntArray1D)->Int): IntArrayND =
        intArrayND(shape.asIntArray1D(), init)

inline fun intArrayND(shape: IntArray1D, init: (indices: IntArray1D)->Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setInt(indices, init(indices))
            }
        }

inline fun intArray1D(size: Int, init: (i0: Int)->Int): IntArray1D =
        NumerikoConfig.defaultFactory.intArray1D(IntArray(size) { init(it) })

inline fun unsafeIntArrayND(shape: IntArray1D, init: (indices: IntArray)->Int): IntArrayND =
        NumerikoConfig.defaultFactory.intZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setInt(indices, init(indices))
            }
        }
