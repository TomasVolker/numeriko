
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.factory.array2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import kotlin.random.Random


operator fun <T> Array2D<T>.get(i0: Int, i1: Int) = getValue(i0, i1)

fun main(args: Array<String>) {


    val a = array2D(2, 3, Array(6) { i -> "$i" })

    println(a)

    println(a[0, 2])

    val b = doubleArray2D(4, 5) { i0, i1 -> (i0 + i1).toDouble() }.also { println(it) }


}
