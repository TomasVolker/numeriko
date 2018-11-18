package tomasvolker.numeriko.sandbox.complex

import tomasvolker.kyplot.dsl.showLine
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D

import tomasvolker.numeriko.complex.exp
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray1D
import tomasvolker.numeriko.complex.interfaces.factory.complexArray2D
import tomasvolker.numeriko.complex.interfaces.factory.complexZeros
import tomasvolker.numeriko.complex.j
import tomasvolker.numeriko.complex.transforms.fht.array1d.dftFromDHT
import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.index.step
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.generic.forEachIndex
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.sandbox.tps.normalPdf
import kotlin.math.PI

fun DoubleArray1D.shiftHalf() =
        doubleArray1D(size) { i ->
            this[(i + size / 2) modulo size]
        }

fun ComplexArray1D.shiftHalf() =
        complexArray1D(size) { i ->
            this[(i + size / 2) modulo size]
        }

fun DoubleArray1D.fft() =
        dftFromDHT(fht(this))


fun ComplexArray1D.myFFT(): ComplexArray1D = when(size) {
    0, 1 -> this
    else -> {
        val even = this[0..Last step 2].myFFT()
        val odd  = this[1..Last step 2].myFFT()
        complexArray1D(size) { k ->
            even[k modulo size / 2] + exp(-2.j * PI * k / size) * odd[k modulo size / 2]
        }
    }
}

fun ComplexArray2D.myFFT(): ComplexArray2D  {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (i0 in indices0) {
        horizontal[i0, All] = this[i0, All].myFFT()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (i1 in indices1) {
        result[All, i1] = horizontal[All, i1].myFFT()
    }

    return result
}



fun main() {

    val n = 128
    val array = complexArray2D(n, n) { i0, i1 ->
        normalPdf(i0.toDouble() / n, mean = 0.2 * i1, deviation = 0.05)
    }


    array.myFFT()

}