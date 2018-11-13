package tomasvolker.numeriko.sandbox

import tomasvolker.kyplot.dsl.*
import tomasvolker.kyplot.model.Legend
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D

import tomasvolker.numeriko.core.linearalgebra.cumSum
import tomasvolker.numeriko.core.linearalgebra.filter1D
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import kotlin.random.Random

fun main() {

    val amount = 1000
    val max = 100.0
    val delta = max / amount

    val time = linearSpace(
            start = 0.0,
            stop = max,
            amount = amount
    )

    val windowSize = 100

    val filter = doubleArray1D(windowSize) { 1.0 }

    val speed = doubleArray1D(time.size) { Random.nextDouble(-0.1, 0.1) }.filter1D(filter)
    val position = speed.cumSum() * delta

    showPlot {
        title = "Random walk"

        line(x = time, y = speed) {
            label = "speed"
        }

        line(x = time, y = position) {
            label = "position"
        }

        legend {
            visible = true
            this.position = Legend.Position.UPPER_LEFT
        }

    }

}