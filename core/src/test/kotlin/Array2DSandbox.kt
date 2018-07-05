import tomasvolker.numeriko.core.interfaces.factory.array2D


fun main(args: Array<String>) {

    val a = array2D(2, 3, Array(6) { i-> "$i" })

    println(a)

}
