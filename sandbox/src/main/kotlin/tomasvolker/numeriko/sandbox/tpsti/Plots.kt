package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.kyplot.dsl.*
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.functions.convolve
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.functions.times
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.primitives.indicator
import tomasvolker.numeriko.core.primitives.modulo
import tomasvolker.numeriko.core.primitives.sqrt
import tomasvolker.numeriko.core.probability.continuous.normalPdf
import kotlin.math.abs
import kotlin.math.sqrt

fun DoubleArray1D.shiftHalf() =
        doubleArray1D(size) { i ->
            this[(i + size / 2) modulo size]
        }

fun main() {

    val space = linearSpace(
            start = -15.0,
            stop = 15.0,
            amount = 301
    )

    val delta = space[1] - space[0]

    val uniform = space.elementWise { x ->
        (abs(x) < sqrt(3.0)).indicator() / (2 * sqrt(3.0))
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
