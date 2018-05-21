package tomasvolker.numeriko.core.plot

import tomasvolker.numeriko.core.interfaces.double.array1d.times
import tomasvolker.numeriko.core.linearalgebra.*
import java.awt.Color
import kotlin.math.PI

fun main(args: Array<String>) {

    val t = linearSpace(
            start = 0.0,
            stop = 1.0,
            count = 101
    )

    val x = linearSpace(start = -2 * PI, stop = 2 * PI, count = 101)

    val plot = Plot().apply {

        addLine(
                x,
                sin(x)
        )

        addLine(
                x,
                cos(x),
                Color.BLUE
        )

        addLine(
                cos(2 * PI * t),
                sin(2 * PI * t),
                Color.RED
        )

        addLine(
                x,
                cosh(x),
                Color.GREEN
        )

        addLine(
                x,
                sinh(x),
                Color.MAGENTA
        )

        addLine(
                x,
                exp(x),
                Color.CYAN
        )

        addLine(
                x,
                exp(-x),
                Color.ORANGE
        )

    }

    //plot.close()

}