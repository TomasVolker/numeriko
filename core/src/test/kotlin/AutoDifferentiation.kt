import tomasvolker.numeriko.core.functional.*
import tomasvolker.numeriko.core.functional.constant.One
import tomasvolker.numeriko.core.functional.constant.Pi
import tomasvolker.numeriko.core.functional.constant.Zero
import tomasvolker.numeriko.core.functional.function1.functions.cos
import tomasvolker.numeriko.core.functional.function1.functions.exp
import tomasvolker.numeriko.core.functional.function1.functions.sin

fun main(args: Array<String>) {

    val x = variable("x")

    val f = 2 * exp(2 * x)

    println(f)

    val dfdx = f.derivative(x)

    println(dfdx)


    println(dfdx.derivative(x))

}