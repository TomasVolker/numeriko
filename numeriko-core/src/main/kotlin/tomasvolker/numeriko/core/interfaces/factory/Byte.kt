package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.config.NumerikoConfig
import tomasvolker.numeriko.core.dsl.I
import tomasvolker.numeriko.core.functions.product
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.arraynd.byte.ByteArrayND
import tomasvolker.numeriko.core.interfaces.iteration.forEachIndex
import tomasvolker.numeriko.core.interfaces.iteration.unsafeForEachIndex

fun ByteArray.asByteArrayND(
        shape: IntArray1D = I[size]
): ByteArrayND = byteArrayND(shape, this)

fun ByteArray.toByteArrayND(
        shape: IntArray1D = I[size]
): ByteArrayND = byteArrayND(shape, this.copyOf())

fun byteArrayND(shape: IntArray, data: ByteArray): ByteArrayND =
        NumerikoConfig.defaultFactory.byteArrayND(shape, data)

fun byteArrayND(shape: IntArray1D, data: ByteArray): ByteArrayND =
        byteArrayND(shape.toIntArray(), data)


fun copy(array: ByteArrayND): ByteArrayND =
        NumerikoConfig.defaultFactory.copy(array)

fun byteZeros(shape: IntArray1D): ByteArrayND =
        NumerikoConfig.defaultFactory.byteZeros(shape)

fun byteFill(shape: IntArray1D, value: Byte): ByteArrayND =
        byteArrayND(shape, ByteArray(shape.product()) { value })

inline fun byteArrayND(vararg shape: Int, init: (indices: IntArray1D)->Number): ByteArrayND =
        byteArrayND(shape.asIntArray1D(), init)

inline fun byteArrayND(shape: IntArray1D, init: (indices: IntArray1D)->Number): ByteArrayND =
        NumerikoConfig.defaultFactory.byteZeros(shape).asMutable().apply {
            forEachIndex { indices ->
                this.setByte(indices, init(indices).toByte())
            }
        }

inline fun unsafeByteArrayND(shape: IntArray1D, init: (indices: IntArray)->Byte): ByteArrayND =
        NumerikoConfig.defaultFactory.byteZeros(shape).asMutable().apply {
            unsafeForEachIndex { indices ->
                this.setByte(indices, init(indices))
            }
        }
