package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.kyplot.dsl.*
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
import tomasvolker.numeriko.core.primitives.sqrt
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

    val gaussians = (1..4).map { variance ->
        space.elementWise {
            normalPdf(it, mean = 0.0, std = sqrt(variance).toDouble())
        }
    }

    showFigure {

        allPlots {
            xAxis.label = "Valeur"
            yAxis.label = "Densité de probabilité"
            position.rowCount = 4
            legend.visible = true
        }

        for(i in 0 until 4) {

            plot {
                line {
                    x = space
                    y = result[i]
                    label = when(i) {
                        0 -> "Uniforme"
                        else -> "Addition de ${i+1} uniformes"
                    }
                }
                line {
                    x = space
                    y = gaussians[i]
                    label = "Gaussienne avec même variance"
                }
                position.row = i
            }

        }

    }

}
