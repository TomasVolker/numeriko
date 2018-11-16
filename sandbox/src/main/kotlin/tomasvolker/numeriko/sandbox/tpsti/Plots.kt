package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showLine
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.numeriko.complex.*
import tomasvolker.numeriko.complex.array.*
import tomasvolker.numeriko.complex.transforms.fft.fft
import tomasvolker.numeriko.complex.transforms.fht.array1d.dftFromDHT
import tomasvolker.numeriko.complex.transforms.fht.array1d.fht
import tomasvolker.numeriko.complex.transforms.fht.array2d.fht
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.primitives.indicative
import tomasvolker.numeriko.core.primitives.isEven
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.probability.scalar.normalPdf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sqrt

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

fun main() {

    val N = 2048

    val c = complexArray1D(N) { n -> normalPdf(n.toDouble() / N, 0.2, 0.001).toComplex() }

    showLine {
        x = c.indices
        y = c.abs
    }

    val fft = c.fastFourierTransform().shiftHalf()

    showPlot {
        line {
            x = c.indices
            y = fft.real
            label = "real"
        }

        line {
            x = c.indices
            y = fft.imag
            label = "imag"
        }

        line {
            x = c.indices
            y = fft.abs
            label = "abs"
        }

        line {
            x = c.indices
            y = fft.arg
            label = "arg"
        }

        legend.visible = true
    }

    val space = linearSpace(
            start = -15.0,
            stop = 15.0,
            amount = 301
    )

    val delta = space[1] - space[0]

    val uniform = space.elementWise { x ->
        (abs(x) < sqrt(3.0)).indicative() / (2 * sqrt(3.0))
    }

    val result = mutableListOf(uniform)

    repeat(4) {
        result.add(
                (result.last() convolve uniform).shiftHalf() * delta
        )
    }

    showPlot {

        title = "Uniform addition distribution"

        xAxis.label = "Value"
        yAxis.label = "Probability density"

        result.forEach {
            line {
                x = space
                y = it
            }
        }

    }

}
