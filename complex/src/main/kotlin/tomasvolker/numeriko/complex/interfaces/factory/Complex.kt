package tomasvolker.numeriko.complex.interfaces.factory

import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.implementations.array1d.NumerikoMutableComplexArray1D
import tomasvolker.numeriko.complex.implementations.array1d.NumerikoMutableComplexArray2D
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
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


fun complexZeros(size: Int): ComplexArray1D =
        NumerikoMutableComplexArray1D(values = doubleZeros(size, 2).asMutable())

fun complexZeros(shape0: Int, shape1: Int): ComplexArray2D =
        NumerikoMutableComplexArray2D(
                real = doubleZeros(shape0, shape1).asMutable(),
                imag = doubleZeros(shape0, shape1).asMutable()
        )


fun complexArray1DOf(vararg complex: Complex): ComplexArray1D =
        complexArray1D(complex)

fun complexArray1D(complex: Array<out Complex>): ComplexArray1D =
        NumerikoMutableComplexArray1D(
                values = doubleArray2D(complex.size, 2) { i0, i1 ->
                    when (i1) {
                        0 -> complex[i0].real
                        1 -> complex[i0].imag
                        else -> throw IllegalStateException()
                    }
                }.asMutable()
        )

inline fun complexArray1D(size: Int, init: (i: Int)->Number): ComplexArray1D {
    val values = doubleZeros(size, 2).asMutable()

    for (i in 0 until size) {
        val complex = init(i).toComplex()
        values[i, 0] = complex.real
        values[i, 1] = complex.imag
    }

    return NumerikoMutableComplexArray1D(
            values = values
    )
}

inline fun complexArray1D(
        size: Int,
        initReal: (i: Int)->Double,
        initImag: (i: Int)->Double
): ComplexArray1D {
    val values = doubleZeros(size, 2).asMutable()

    for (i in 0 until size) {
        values[i, 0] = initReal(i)
        values[i, 1] = initImag(i)
    }

    return NumerikoMutableComplexArray1D(
            values = values
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

    return NumerikoMutableComplexArray2D(
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

    return NumerikoMutableComplexArray2D(
            real = real,
            imag = imag
    )
}
