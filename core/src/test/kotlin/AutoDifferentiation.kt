import tomasvolker.numeriko.core.simbolic.*
import tomasvolker.numeriko.core.simbolic.function1.Function1
import tomasvolker.numeriko.core.simbolic.function1.differentiableFunction1
import tomasvolker.numeriko.core.simbolic.function1.functions.*
import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.plot.line
import tomasvolker.numeriko.core.plot.plot
import tomasvolker.numeriko.core.simbolic.constant.One
import tomasvolker.numeriko.core.simbolic.expression.DifferentiableExpression
import tomasvolker.numeriko.core.simbolic.function1.DifferentiableFunction1
import tomasvolker.numeriko.core.simbolic.function1.operators.Identity
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

    val cost = differentiableFunction1 { 2 * (it -1)  * it }

    val x1 = variable("x1")
    val x2 = variable("x2")

    val expr = x1 * x2

    println(expr(x1 to x2))


    val optimizer = GradientDescentOptimizer(
            cost = cost,
            seed = -0.5,
            alpha = 0.1
    )

    repeat(100) {

        println(optimizer.current)
        optimizer.step()

    }

}