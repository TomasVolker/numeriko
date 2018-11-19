package tomasvolker.numeriko.sandbox.complex

import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showImage
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.kyplot.model.Plot
import tomasvolker.numeriko.complex.Complex
import tomasvolker.numeriko.complex.dsl.C
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D

import tomasvolker.numeriko.complex.exp
import tomasvolker.numeriko.complex.interfaces.array1d.elementWise
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.factory.*
import tomasvolker.numeriko.complex.j
import tomasvolker.numeriko.complex.toComplex
import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.core.dsl.A
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.All
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.index.rangeTo
import tomasvolker.numeriko.core.index.step
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices0
import tomasvolker.numeriko.core.interfaces.array2d.generic.indices1
import tomasvolker.numeriko.core.interfaces.array2d.generic.toListOfLists
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.sandbox.image.loadGrayScaleImage
import tomasvolker.numeriko.sandbox.image.showImage
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sin

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


fun dftFromDHT(
        dht: DoubleArray1D
): ComplexArray1D =
        complexArray1D(
            size = dht.size,
            initReal = { k ->  (dht[k] + dht[(-k) modulo dht.size]) / 2 },
            initImag = { k -> -(dht[k] - dht[(-k) modulo dht.size]) / 2 }
        )


fun DoubleArray1D.fft() = dftFromDHT(this.fht())


fun ComplexArray1D.fft(): ComplexArray1D = when(size) {
    0, 1 -> this
    else -> {
        val even = this[0..Last step 2].fft()
        val odd  = this[1..Last step 2].fft()
        complexArray1D(size) { k ->
            even[k modulo size / 2] + exp(-2.j * PI * k / size) * odd[k modulo size / 2]
        }
    }
}

fun ComplexArray1D.ifft(): ComplexArray1D {
    val fft = fft()
    return complexArray1D(size) { k ->
        fft[-k modulo size] / size
    }
}

fun DoubleArray1D.fht(): DoubleArray1D = when(size) {
    0, 1 -> this
    else -> {
        val even = this[0..Last step 2].fht()
        val odd  = this[1..Last step 2].fht()

        doubleArray1D(size) { k ->
            even[k modulo size / 2] +
            cos(2 * PI * k / size) * odd[k modulo size / 2] +
            sin(2 * PI * k / size) * odd[(size / 2 - k) modulo size / 2]
        }
    }
}

fun ComplexArray2D.fft2D(): ComplexArray2D  {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (n0 in indices0) {
        horizontal[n0, All] = this[n0, All].fft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (n1 in indices1) {
        result[All, n1] = horizontal[All, n1].fft()
    }

    return result
}

fun ComplexArray2D.ifft2D(): ComplexArray2D  {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (i0 in indices0) {
        horizontal[i0, All] = this[i0, All].ifft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (i1 in indices1) {
        result[All, i1] = horizontal[All, i1].ifft()
    }

    return result
}


fun DoubleArray2D.fft2D(): ComplexArray2D  {

    val horizontal = complexZeros(shape0, shape1).asMutable()

    for (i0 in indices0) {
        horizontal[i0, All] = this[i0, All].fft()
    }

    val result = complexZeros(shape0, shape1).asMutable()

    for (i1 in indices1) {
        result[All, i1] = horizontal[All, i1].fft()
    }

    return result
}


fun main() {

    val image = loadGrayScaleImage("./sandbox/res/lena.png")

    val fft = image.fft2D()

    showImage(image)

    showImage(fft.abs.elementWise { ln(it) }.shiftHalf())

    showImage(fft.ifft2D().real)

}

fun showLine(array: ComplexArray1D) {

    showPlot {
        complexLine(array)
    }

}

fun Plot.Builder.complexLine(array: ComplexArray1D) {

    line {
        x = array.indices
        y = array.real
        label = "real"
    }

    line {
        x = array.indices
        y = array.imag
        label = "imag"
    }
    legend.visible = true

}

fun showLine(array: DoubleArray1D) {

    showPlot {
        realLine(array)
    }

}

fun Plot.Builder.realLine(array: DoubleArray1D) {

    line {
        x = array.indices
        y = array
    }

}