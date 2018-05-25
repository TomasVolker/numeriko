import tomasvolker.simboliko.function1.Function1
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.plot.line
import tomasvolker.numeriko.core.plot.plot
import tomasvolker.simboliko.function1.DifferentiableFunction1
import tomasvolker.simboliko.function1.functions.cos
import tomasvolker.simboliko.function1.functions.exp
import tomasvolker.simboliko.function1.functions.sin
import tomasvolker.simboliko.function1.operators.Identity
import tomasvolker.simboliko.plus
import tomasvolker.simboliko.times
import tomasvolker.simboliko.variable
import java.awt.Color

operator fun Function1.invoke(array: DoubleArray1D) =
        array.elementWise { this(it) }

class GradientDescentOptimizer(
        val cost: DifferentiableFunction1,
        seed: Double,
        val alpha: Double
){

    val derivative = cost.derivative()

    var current = seed

    fun step() {
        current -= alpha * derivative(current)
    }

}


fun main(args: Array<String>) {

    val X = Identity

    val f = exp(cos(2 * X) / (2 + sin(X)))
    val df = f.derivative()
    val ddf = df.derivative()

    println(f)
    println(df)
    println(ddf)

    val x = linearSpace(
            start = -10.0,
            stop = 10.0,
            amount = 301
    )

    plot {

        line(x, f(x)) {
            color = Color.RED
        }

        line(x, df(x)) {
            color = Color.GREEN
        }

        line(x, ddf(x)) {
            color = Color.BLUE
        }

    }



    val x1 = variable("x1")
    val x2 = variable("x2")

    val expr = cos(x1 - x2)

    println(expr.derivative(x1))

/*
    val cost = differentiableFunction1 { 2 * (it -1)  * it }

    val optimizer = GradientDescentOptimizer(
            cost = cost,
            seed = -0.5,
            alpha = 0.1
    )

    repeat(100) {

        println(optimizer.current)
        optimizer.step()

    }
*/
}