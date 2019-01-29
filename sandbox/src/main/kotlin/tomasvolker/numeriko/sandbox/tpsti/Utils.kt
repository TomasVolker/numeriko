package tomasvolker.numeriko.sandbox.tpsti

import org.ejml.data.DMatrixRMaj
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.sandbox.tps.squared
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.DoubleBuffer
import java.util.*
import kotlin.math.cosh
import kotlin.math.log10
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream
import java.io.ByteArrayInputStream
import org.opencv.core.CvType.channels
import tomasvolker.numeriko.core.interfaces.array1d.double.*
import tomasvolker.numeriko.core.primitives.pow2
import javax.sound.sampled.AudioFormat
import kotlin.math.absoluteValue


val javaRandom = Random()

inline fun sumVector(indices: IntProgression, value: (i: Int)-> DoubleArray1D): DoubleArray1D {
    var result: MutableDoubleArray1D? = null
    for (i in indices) {
        result = if (result == null)
            value(i).copy().asMutable()
        else
            result.applyPlus(value(i))
    }
    return result ?: throw IllegalArgumentException()
}


infix fun DoubleArray1D.colinearityFactor(other: DoubleArray1D): Double =
        (this inner other) / (this.norm2() * other.norm2())

fun sech(x: Double): Double = 1.0 / cosh(x)

operator fun MutableDoubleArray2D.set(i0: IndexProgression, i1: Int, array: DoubleArray1D) =
        getView(i0, i1).setValue(array)

fun DoubleArray2D.toEjmlMatrix(): DMatrixRMaj {
    val mat = DMatrixRMaj(shape0, shape1)
    forEachIndex { i0, i1 ->
        mat.set(i0, i1, this[i0, i1])
    }
    return mat
}

fun DMatrixRMaj.toDoubleArray2D(): DoubleArray2D =
        doubleArray2D(numRows, numCols) { i0, i1 ->
            this[i0, i1]
        }

fun DMatrixRMaj.toDoubleArray1D(): DoubleArray1D =
        doubleArray1D(numRows) { i ->
            this[i]
        }

fun File.readArray1D(byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN): DoubleArray1D =
        inputStream().use { readBytes().toDoubleArray1D(byteOrder) }

fun ByteArray.toDoubleArray1D(
        byteOrder: ByteOrder=  ByteOrder.BIG_ENDIAN
): DoubleArray1D {
    val buffer = ByteBuffer.wrap(this).apply {
        order(byteOrder)
    }.asDoubleBuffer()

    return doubleArray1D(buffer.limit()) { i ->
        buffer[i]
    }
}

fun ByteArray.toDoubleArray2D(
        shape0: Int,
        shape1: Int,
        byteOrder: ByteOrder=  ByteOrder.BIG_ENDIAN
): DoubleArray2D {
    val buffer = ByteBuffer.wrap(this).apply {
        order(byteOrder)
    }.asDoubleBuffer()

    return doubleArray2D(shape0, shape1) { i0, i1 ->
        buffer[i0 * shape1 + i1]
    }
}

fun DoubleBuffer.toDoubleArray(): DoubleArray =
        DoubleArray(limit()) { i -> this[i] }

fun ByteArray.toDoubleArrayND(
        shape: IntArray1D,
        byteOrder: ByteOrder=  ByteOrder.BIG_ENDIAN
): DoubleArrayND {
    val buffer = ByteBuffer.wrap(this).apply {
        order(byteOrder)
    }.asDoubleBuffer()

    return doubleArrayND(shape, buffer.toDoubleArray())
}


fun normalizedQuadraticError(array1: DoubleArray1D, array2: DoubleArray1D): Double =
        1 - (array1 colinearityFactor array2).squared()

fun Double.toDB(): Double = 10 * log10(this)


fun DoubleArray1D.writeToWav(file: File, maxAmplitude: Double? = null) {

    val normalization = 1.0 / (maxAmplitude ?: maxBy { it.absoluteValue } ?: 1.0)

    val maxShort = Short.MAX_VALUE

    val byteBuffer = ByteArray(this.size * 2)

    for (i in 0 until byteBuffer.size step 2) {
        val x = (this[i/2] * normalization * maxShort).toInt()
        byteBuffer[i] = x.toByte()
        byteBuffer[i+1] = x.ushr(8).toByte()
    }

    val format = audioFormat(
            sampleRate = 44100.0,
            bits = 16,
            channels = 1,
            signed = true,
            bigEndian = false
    )

    byteBuffer.inputStream().audioStream(format, length = this.size.toLong()).use {
        AudioSystem.write(it, AudioFileFormat.Type.WAVE, file)
    }
}

fun ByteArrayInputStream.audioStream(
        format: AudioFormat,
        length: Long
) = AudioInputStream(this, format, length)

fun audioFormat(
        sampleRate: Double,
        bits: Int,
        channels: Int,
        signed: Boolean,
        bigEndian: Boolean
) = AudioFormat(
        sampleRate.toFloat(),
        bits,
        channels,
        signed,
        bigEndian
)