package tomasvolker.numeriko.zoo.solving

import tomasvolker.numeriko.core.dsl.ar
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.linearalgebra.inverse
import tomasvolker.numeriko.core.linearalgebra.matMul
import tomasvolker.numeriko.core.linearalgebra.solve
import tomasvolker.numeriko.core.primitives.indicative
import kotlin.math.absoluteValue

fun identity(size: Int): DoubleArray2D = doubleArray2D(size, size) { i0, i1 ->
    (i0 == i1).indicative()
}

fun main() {

    val tolerance = 1e-10

    infix fun DoubleArray2D.numericEquals(other: DoubleArray2D): Boolean =
            this.shape == other.shape &&
            this.zip(other).all { (it.first - it.second).absoluteValue < tolerance }

    val matrix = ar[ar[ 86.0, 6.0, -4.5],
                    ar[ 16.0, 4.0,  2.0],
                    ar[ -3.0, 0.6,  1.0]]

    val x = ar[ 5.5, 6.3, -7.0]

    val b = matrix matMul x

    val inv = matrix.inverse()

    val solved = matrix.solve(b)

    println(solved)

}