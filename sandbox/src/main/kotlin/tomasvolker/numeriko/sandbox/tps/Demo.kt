package tomasvolker.numeriko.sandbox.tps

import tomasvolker.kyplot.dsl.color
import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.kyplot.model.Color
import tomasvolker.numeriko.core.functions.cumulativeSum
import tomasvolker.numeriko.core.interfaces.factory.*
import kotlin.random.Random


fun main() {

    val n = 100
    val time = 0 until n
    val steps = Random.nextGaussianArray1D(n)
    val positions = steps.cumulativeSum()

    showPlot {
        title = "Random walk"
        line {
            x = time
            y = steps
            color = Color.BLUE
            label = "Steps"
        }
        line {
            x = time
            y = positions
            color = Color.RED
            label = "Positions"
        }
        xAxis.label = "Time"
        yAxis.label = "Distance"
        legend.visible = true
        grid.visible = true
    }

}