
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D

import tomasvolker.numeriko.core.linearalgebra.convolve
import tomasvolker.numeriko.core.linearalgebra.cumSum
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.plot.line
import tomasvolker.numeriko.core.plot.plot
import java.awt.Color
import java.util.*

fun main(args: Array<String>) {

    val x = linearSpace(start = -5.0, stop = 5.0, amount = 10000)

    val random = Random()

    val windowSize = 100

    val squareWindow = doubleArray1D(x.size) { i ->
        if(i < windowSize)
            1.0 / windowSize
        else
            0.0
    }

    fun Random.nextDouble(min: Double, max: Double) =
            min + (max - min) * nextDouble()

    // Circular convolution
    val lowPass = squareWindow convolve squareWindow convolve squareWindow

    val speed = doubleArray1D(x.size) { random.nextDouble(-0.1, 0.1) } convolve lowPass
    val position = speed.cumSum() / 2

    plot {

        line(x = x, y = lowPass * windowSize) {
            color = Color.RED
        }

        line(x = x, y = speed * 100) {
            color = Color.BLUE
        }

        line(x = x, y = position) {
            color = Color.GREEN
        }

    }

}