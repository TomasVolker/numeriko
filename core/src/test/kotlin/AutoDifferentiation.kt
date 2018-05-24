import tomasvolker.numeriko.core.simbolic.*
import tomasvolker.numeriko.core.simbolic.function1.Function1
import tomasvolker.numeriko.core.simbolic.function1.differentiableFunction1
import tomasvolker.numeriko.core.simbolic.function1.functions.*
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.plot.line
import tomasvolker.numeriko.core.plot.plot
import java.awt.Color

operator fun Function1.invoke(array: DoubleArray1D) =
        array.elementWise { this(it) }

fun main(args: Array<String>) {

    val f = differentiableFunction1 { exp(cos(2 * it) / (2 + sin(it))) }
    val df = f.derivative()
    val ddf = df.derivative()

    val x = linearSpace(
            start = -10.0,
            stop = 10.0,
            amount = 301
    )

    plot {

        line(x, f(x)) {
            color = Color.RED
            lineWidth = 2.0
        }

        line(x, df(x)) {
            color = Color.GREEN
        }

        line(x, ddf(x)) {
            color = Color.BLUE
        }

    }

}