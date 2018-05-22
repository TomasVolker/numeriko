package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.mutableIntArray1D
import tomasvolker.numeriko.core.linearalgebra.convolve
import tomasvolker.numeriko.core.linearalgebra.cumSum
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import java.awt.Color
import java.util.*

fun main(args: Array<String>) {

    val a = mutableIntArray1D(10) { it }

    println(a)

    a[3..8] = a[0..5]

    println(a)

    val t = linearSpace(
            start = 0.0,
            stop = 1.0,
            amount = 101
    )

    val x = linearSpace(start = -5.0, stop = 5.0, amount = 10000)

    val random = Random()

    fun Random.nextDouble(min: Double, max: Double) =
            min + (max - min) * nextDouble()

    val windowSize = 1000

    val squareWindow = doubleArray1D(x.size) { i ->
        if(i < windowSize)
            1.0 / windowSize
        else
            0.0
    }

    // Circular convolution
    val lowPass = squareWindow convolve squareWindow convolve squareWindow

    val speed = doubleArray1D(x.size) { random.nextDouble(-0.1, 0.1) } convolve lowPass
    val position = speed.cumSum() / 2

    plot {

        line(x = x, y = lowPass * windowSize) {
            color = Color.RED
        }

        line(x = x, y = speed * 1000) {
            color = Color.BLUE
        }

        line(x = x, y = position) {
            color = Color.GREEN
        }

    }

/*

    val alphaRange = linearSpace(-1.0, 1.0, 5)

    plot {

        for (alpha in alphaRange) {

            line(x = x, y = exp(alpha * x)) {
                color = if(alpha < 0) Color.RED else Color.BLUE
                lineWidth = if(alpha > 0) 2.0 else 1.0
            }

        }

        line(
            x = cos(2 * PI * t),
            y = sin(2 * PI * t),
            style = style {
                color = Color.RED
            }
        )

    }


    plot {

        line(x, sin(x))

        line(x, cos(x)) {
            color = Color.BLUE
        }

        line(
                x = cos(2 * PI * t),
                y = sin(2 * PI * t)
        )

        line(x, cosh(x)) {
            color = Color.GREEN
        }

        line(x, sinh(x)) {
            color = Color.MAGENTA
        }

        line(x, exp(x)) {
            color = Color.CYAN
        }

        line(x, exp(-x)) {
            color = Color.ORANGE
        }

    }

*/
}