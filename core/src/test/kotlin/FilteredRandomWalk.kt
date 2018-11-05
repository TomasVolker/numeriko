import perchanegro.kyplot.dsl.*
import perchanegro.kyplot.model.Color
import perchanegro.kyplot.model.Legend
import perchanegro.kyplot.model.MarkerStyle
import perchanegro.kyplot.model.MarkerType
import perchanegro.kyplot.model.drawing.Line
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D

import tomasvolker.numeriko.core.linearalgebra.convolve
import tomasvolker.numeriko.core.linearalgebra.cumSum
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import java.util.*

fun main() {

    val amount = 1000
    val max = 100.0
    val delta = max / amount

    val time = linearSpace(
            start = 0.0,
            stop = max,
            amount = amount
    )

    val random = Random()

    val windowSize = 3

    val squareWindow = time.elementWise { t ->
        if(t < windowSize)
            1.0 / windowSize
        else
            0.0
    }


    fun Random.nextDouble(min: Double, max: Double) =
            min + (max - min) * nextDouble()

    // Circular convolution
    val lowPass = (squareWindow convolve squareWindow convolve squareWindow) * delta * delta

    val speed = doubleArray1D(time.size) { random.nextDouble(-0.1, 0.1) } convolve lowPass
    val position = speed.cumSum() * delta

    showPlot {
        title = "Random walk"

        line(x = time, y = lowPass * windowSize) {
            label = "filter"
        }

        line(x = time, y = speed) {
            label = "speed"
        }

        line(x = time, y = position) {
            label = "position"
        }

        legend {
            visible = true
            this.position = Legend.Position.LOWER_RIGHT
        }

    }

}