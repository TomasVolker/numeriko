package tomasvolker.numeriko.sandbox.complex

import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.kyplot.model.Plot
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.generic.indices
import tomasvolker.numeriko.core.interfaces.array2d.double.elementWise
import tomasvolker.numeriko.sandbox.image.loadGrayScaleImage
import tomasvolker.numeriko.sandbox.image.showImage
import kotlin.math.ln


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