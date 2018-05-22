package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.double.array1d.times
import tomasvolker.numeriko.core.linearalgebra.*
import java.awt.Color
import kotlin.math.PI

fun main(args: Array<String>) {

    val t = linearSpace(
            start = 0.0,
            stop = 1.0,
            amount = 101
    )

    val x = linearSpace(start = -2 * PI, stop = 2 * PI, amount = 101)

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


}