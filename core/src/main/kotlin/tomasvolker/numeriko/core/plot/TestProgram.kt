package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.mutableDoubleArray1D
import tomasvolker.numeriko.core.linearalgebra.convolve
import tomasvolker.numeriko.core.linearalgebra.cumSum
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import java.awt.Color

fun main(args: Array<String>) {

    val t = linearSpace(
            start = 0.0,
            stop = 1.0,
            amount = 101
    )

    val x = linearSpace(start = -5.0, stop = 5.0, amount = 10000)

    val avg = 1000

    var lowPass = mutableDoubleArray1D(x.size) { i ->
        if(i < avg)
            1.0/avg
        else
            0.0
    }

    lowPass = lowPass convolve lowPass convolve lowPass

    val random = doubleArray1D(x.size) { 0.1 * (Math.random() - 0.5) } convolve lowPass
    val walk = random.cumSum() / 2

    plot {

        line(x = x, y = lowPass * avg) {
            color = Color.RED
        }

        line(x = x, y = random * 1000) {
            color = Color.BLUE
        }

        line(x = x, y = walk) {
            color = Color.GREEN
        }

    }


    val alphaRange = linearSpace(-1.0, 1.0, 5)

/*
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