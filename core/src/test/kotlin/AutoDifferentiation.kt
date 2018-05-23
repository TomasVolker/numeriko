import tomasvolker.numeriko.core.functional.double.ConstantFunction
import tomasvolker.numeriko.core.functional.double.affineFunction
import tomasvolker.numeriko.core.functional.double.asConstantFunction
import tomasvolker.numeriko.core.functional.double.constantFunction

fun main(args: Array<String>) {

    val f = affineFunction(2.0, 5.0)

    val x = 5.0.asConstantFunction()

    println(f)

    val df = f.differentiate()

    println(df)

    val ddf = df.differentiate()

    println(ddf)

}