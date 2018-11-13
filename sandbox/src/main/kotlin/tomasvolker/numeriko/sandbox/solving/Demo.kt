package tomasvolker.numeriko.sandbox.solving

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleIdentity
import tomasvolker.numeriko.core.linearalgebra.determinant
import tomasvolker.numeriko.core.linearalgebra.inverse
import tomasvolker.numeriko.core.linearalgebra.matMul
import tomasvolker.numeriko.core.linearalgebra.solve
import tomasvolker.numeriko.core.primitives.indicative
import kotlin.math.absoluteValue

fun main() {

    val tolerance = 1e-10

    infix fun DoubleArray2D.numericEquals(other: DoubleArray2D): Boolean =
            this.shape == other.shape &&
            this.zip(other).all { (it.first - it.second).absoluteValue < tolerance }

    val matrix = D[D[ 86,   6, -4],
                   D[ 16,   4,  2],
                   D[ -3, 0.6,  1]]

    val x = D[ 5.5, 6.3, -7]

    val b = matrix matMul x

    val inv = matrix.inverse()

    val solved = matrix.solve(b)

    println(solved)

}