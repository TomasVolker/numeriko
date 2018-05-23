import tomasvolker.numeriko.core.functional.affine.linear.IdentityFunction
import tomasvolker.numeriko.core.functional.Sine
import tomasvolker.numeriko.core.functional.asConstant

fun main(args: Array<String>) {

    val x = IdentityFunction

    (2.asConstant() * x)

    val f = Sine(2.asConstant() * x)

    println(f)

    val df = f.differentiate()

    println(df)

    val ddf = df.differentiate()

    println(ddf)

}