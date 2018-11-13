package tomasvolker.numeriko.sandbox.tpsti

import org.ejml.data.DMatrixRMaj
import tomasvolker.numeriko.core.functions.norm2
import tomasvolker.numeriko.core.index.IndexProgression
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.MutableDoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArrayND
import tomasvolker.numeriko.core.linearalgebra.inner
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.DoubleBuffer
import java.util.*
import kotlin.math.cosh

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
