
import tomasvolker.numeriko.core.dsl.ar
import tomasvolker.numeriko.core.index.*
import tomasvolker.numeriko.core.interfaces.array2d.generic.Array2D
import tomasvolker.numeriko.core.interfaces.factory.array2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1DOf
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.linearalgebra.matMul


operator fun <T> Array2D<T>.get(i0: Int, i1: Int) = getValue(i0, i1)

fun main(args: Array<String>) {

    val a = array2D(2, 3, Array(6) { i -> "$i" })

    println(a)

    println(a[0, 2])

    val b = doubleArray2D(3, 2) { i0, i1 -> (i0 - i1).toDouble() }

    println(b)

    val c = b matMul ar[1.0, 2.0]

    println(c)

    ar[1.0, 2.0, 3.0]

    val mat = ar[ar[1.0, 2.0, 3.0],
                 ar[2.0, 3.0, 5.0]]

    println(mat[0, 1])

}
