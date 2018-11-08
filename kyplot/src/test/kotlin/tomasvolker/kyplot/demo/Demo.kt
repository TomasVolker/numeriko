package tomasvolker.kyplot.demo

import tomasvolker.kyplot.dsl.*
import tomasvolker.kyplot.model.*
import java.util.Random
import kotlin.math.*

val random = Random()

fun main() {

    val xs = List(1000) {
        random.nextGaussian()
    }

    val ys = List(1000) {
        random.nextGaussian()
    }

    val abs = xs.zip(ys).map { hypot(it.first, it.second) }

    val xSpace = List(100) { i -> -5 + 10 * i.toDouble() / 100 }
    val gaussianPdf = xSpace.map { exp(-(it * it)/2) / (sqrt(2 * PI)) }

    showPlot {

        title = "Samples"

        xAxis.limits = between(-5, 5)

        yAxis.limits = between(-5, 5)

        scatter(x = xs, y = ys) {
            markerStyle.type = MarkerType.X
            markerStyle.color = Color.RED
            markerStyle.alpha = 0.5
        }
    }

    showHistogram(title = "Absolute value") {
        data = abs
        bins = 40
    }

    showPlot {

        title = "Gaussian"

        xAxis.label = "Samples"
        yAxis.label = "Probability density"

        histogram(xs) {
            bins = 40
            normalized = true
            color = Color.BLUE
        }

        line {
            x = xSpace
            y = gaussianPdf

            lineStyle {
                color = Color.RED
                type = LineType.DASHED
                width = 2
            }

        }
    }

}
