package tomasvolker.numeriko.sandbox.complex

import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showImage
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.kyplot.model.Plot
import tomasvolker.numeriko.complex.*
import tomasvolker.numeriko.complex.dsl.C
import tomasvolker.numeriko.complex.interfaces.array1d.ComplexArray1D

import tomasvolker.numeriko.complex.interfaces.array1d.elementWise
import tomasvolker.numeriko.complex.interfaces.array2d.ComplexArray2D
import tomasvolker.numeriko.complex.interfaces.arraynd.unsafeGetView
import tomasvolker.numeriko.complex.interfaces.factory.*
import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.core.dsl.A
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.dsl.I
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


fun main() {
/*
    val c3d = complexArrayND(I[2,3,4]) { (i0, i1, i2) ->
        i0 + i1.j + 10 * i2
    }
    */

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