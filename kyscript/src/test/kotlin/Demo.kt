import tomasvolker.kyscript.*

fun main() {

    val script = kyScript {

        importAs("numpy", alias = "np")

        newLine(2)

        comment(
            """
        |Auto generated script
        """.trimMargin()
        )

        newLine()

        val x = id("x")
        val print = id("print")
        val range = id("range")

        x assign 8

        newLine()

        ifThen(inject("x > 5")) {
            +print(x)
        }

        newLine()

        val i = id("i")

        forEach(i, range(0, x)) {
            +print(i)
        }

    }

    println(script)

    val result = readKyScript(script)

    println(result)

}