package tomasvolker.numeriko.complex.interfaces.factory

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.implementations.array0d.NumerikoComplexArray0D
import tomasvolker.numeriko.complex.implementations.array1d.NumerikoComplexArray1D
import tomasvolker.numeriko.complex.implementations.array2d.NumerikoComplexArray2D
import tomasvolker.numeriko.complex.implementations.arraynd.NumerikoComplexArrayND
import tomasvolker.numeriko.complex.interfaces.array0d.ComplexArray0D
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.ComplexArrayND
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.arraynd.generic.forEachIndices
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleZeros

fun DoubleArray1D.toComplexArray(): ComplexArray1D =
        complexArray1D(size) { i ->
            this[i].toComplex()
        }

inline fun ComplexArray1D.elementWiseIndexed(
        operation: (i: Int, Complex)-> Complex
): ComplexArray1D {
    val result = complexZeros(size).asMutable()
    for (i in indices) {
        result[i] = operation(i, this[i])
    }
    return result
}

fun copy(complex: ComplexArray0D): ComplexArray0D =
        NumerikoComplexArray0D(complex.real(), complex.imag())

fun copy(array: ComplexArray1D): ComplexArray1D =
        complexArray1D(
                size = array.size,
                initReal = { i -> array.real(i) },
                initImag = { i -> array.imag(i) }
        )

fun copy(array: ComplexArray2D): ComplexArray2D =
        complexArray2D(
                shape0 = array.shape0,
                shape1 = array.shape1,
                initReal = { i0, i1 -> array.real(i0, i1) },
                initImag = { i0, i1 -> array.imag(i0, i1) }
        )

fun copy(array: ComplexArrayND): ComplexArrayND =
        complexArrayND(
                shape = array.shape,
                initReal = { indices -> array.real(*indices.toIntArray()) },
                initImag = { indices -> array.imag(*indices.toIntArray()) }
        )


fun complexZeros(size: Int): ComplexArray1D =
        NumerikoComplexArray1D(
                real = doubleZeros(size).asMutable(),
                imag = doubleZeros(size).asMutable()
        )

fun complexZeros(shape0: Int, shape1: Int): ComplexArray2D =
        NumerikoComplexArray2D(
                real = doubleZeros(shape0, shape1).asMutable(),
                imag = doubleZeros(shape0, shape1).asMutable()
        )

fun complexZeros(shape: IntArray1D): ComplexArrayND =
        NumerikoComplexArrayND(
                real = doubleZeros(shape).asMutable(),
                imag = doubleZeros(shape).asMutable()
        )

fun complexArray0D(complex: Complex): ComplexArray0D =
        NumerikoComplexArray0D(complex.real, complex.imag)


fun complexArray1DOf(vararg complex: Complex): ComplexArray1D =
        complexArray1D(complex)

fun complexArray1D(complex: Array<out Complex>): ComplexArray1D =
        NumerikoComplexArray1D(
                real = doubleArray1D(complex.size) { i -> complex[i].real }.asMutable(),
                imag = doubleArray1D(complex.size) { i -> complex[i].imag }.asMutable()
        )

inline fun complexArray1D(size: Int, init: (i: Int)->Number): ComplexArray1D {
    val real = doubleZeros(size).asMutable()
    val imag = doubleZeros(size).asMutable()

    for (i in 0 until size) {
        val complex = init(i).toComplex()
        real[i] = complex.real
        imag[i] = complex.imag
    }

    return NumerikoComplexArray1D(
            real = real,
            imag = imag
    )
}

inline fun complexArray1D(
        size: Int,
        initReal: (i: Int)->Double,
        initImag: (i: Int)->Double
): ComplexArray1D {
    val real = doubleZeros(size).asMutable()
    val imag = doubleZeros(size).asMutable()

    for (i in 0 until size) {
        real[i] = initReal(i)
        imag[i] = initImag(i)
    }

    return NumerikoComplexArray1D(
            real = real,
            imag = imag
    )
}

inline fun complexArray2D(
        shape0: Int,
        shape1: Int,
        init: (i0: Int, i1: Int)->Number
): ComplexArray2D {
    val real = doubleZeros(shape0, shape1).asMutable()
    val imag = doubleZeros(shape0, shape1).asMutable()

    real.forEachIndex { i0, i1 ->
        val complex = init(i0, i1).toComplex()
        real[i0, i1] = complex.real
        imag[i0, i1] = complex.imag

    }

    return NumerikoComplexArray2D(
            real = real,
            imag = imag
    )
}

inline fun complexArray2D(
        shape0: Int,
        shape1: Int,
        initReal: (i0: Int, i1: Int)->Double,
        initImag: (i0: Int, i1: Int)->Double
): ComplexArray2D {
    val real = doubleZeros(shape0, shape1).asMutable()
    val imag = doubleZeros(shape0, shape1).asMutable()

    real.forEachIndex { i0, i1 ->
        real[i0, i1] = initReal(i0, i1)
        imag[i0, i1] = initImag(i0, i1)

    }

    return NumerikoComplexArray2D(
            real = real,
            imag = imag
    )
}

inline fun complexArrayND(
        shape: IntArray1D,
        init: (indices: IntArray1D)->Number
): ComplexArrayND {
    val real = doubleZeros(shape).asMutable()
    val imag = doubleZeros(shape).asMutable()

    real.forEachIndices { indices ->
        val complex = init(indices).toComplex()
        real[indices] = complex.real
        imag[indices] = complex.imag
    }

    return NumerikoComplexArrayND(
            real = real,
            imag = imag
    )
}

inline fun complexArrayND(
        shape: IntArray1D,
        initReal: (indices: IntArray1D)->Number,
        initImag: (indices: IntArray1D)->Number
): ComplexArrayND {
    val real = doubleZeros(shape).asMutable()
    val imag = doubleZeros(shape).asMutable()

    real.forEachIndices { indices ->
        real[indices] = initReal(indices).toDouble()
        imag[indices] = initImag(indices).toDouble()
    }

    return NumerikoComplexArrayND(
            real = real,
            imag = imag
    )
}