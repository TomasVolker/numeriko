import tomasvolker.numeriko.core.functional.*

fun main(args: Array<String>) {

    val x = variable("x")

    val f = Sine(2 * x)

    println(f)

    val df = f.derivative(x)

    println(df)

    val ddf = df.derivative(x)

    println(ddf)

}