import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.function1.functions.cos
import tomasvolker.numeriko.core.functional.function1.functions.sin
import tomasvolker.numeriko.core.functional.function2.operators.plus

fun main(args: Array<String>) {

    val x = variable("x")
    val y = variable("y")

    val f = sin(2 * x) + cos(3 * y)

    println(f)

    val df = f.derivative(x)

    println(df)

    val ddf = df.derivative(x)

    println(ddf)

    println(ddf.variables())

}