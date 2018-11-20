package tomasvolker.numeriko.sandbox.complex

import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray2D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.modulo

fun DoubleArray1D.shiftHalf() =
        doubleArray1D(size) { i ->
            this[(i + size / 2) modulo size]
        }

fun DoubleArray2D.shiftHalf() =
        doubleArray2D(shape0, shape1) { i0, i1 ->
            this[(i0 + shape0 / 2) modulo shape0, (i1 + shape1 / 2) modulo shape1]
        }

fun ComplexArray1D.shiftHalf() =
        complexArray1D(size) { i ->
            this[(i + size / 2) modulo size]
        }

fun ComplexArray2D.shiftHalf() =
        complexArray2D(shape0, shape1) { i0, i1 ->
            this[(i0 + shape0 / 2) modulo shape0, (i1 + shape1 / 2) modulo shape1]
        }