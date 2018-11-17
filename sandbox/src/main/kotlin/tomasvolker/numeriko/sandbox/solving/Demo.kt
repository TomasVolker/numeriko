package tomasvolker.numeriko.sandbox.solving

import tomasvolker.kyplot.dsl.histogram
import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.nextDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.nextGaussianArray1D
import tomasvolker.numeriko.core.interfaces.factory.nextGaussianArray2D
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.sandbox.tps.normalPdf
import kotlin.math.absoluteValue
import kotlin.random.Random

fun main() {

    val a = doubleArray1D(10) { it.toDouble() }

    val tolerance = 1e-10

    infix fun DoubleArray2D.numericEquals(other: DoubleArray2D): Boolean =
            this.shape == other.shape &&
            this.zip(other).all { (it.first - it.second).absoluteValue < tolerance }

    val matrix = D[D[ 86,   6, -4],
                   D[ 16,   4,  2],
                   D[ -3, 0.6,  1]]

    val xs = D[ 5.5, 6.3, -7]

    val b = matrix matMul xs

    val inv = matrix.inverse()

    val solved = matrix.solve(b)

    println(solved)

    val a1 = D[1, 2, 3]
    val a2 = D[3, 4, 5]

    println((a1 outer a2).contract(0, 1))
    println(a1 inner a2)
/*
    println((matrix outer a2).contract(1, 2))
    println(matrix matMul a2)
*/

    repeat(5) {
        showPlot {

            histogram {
                data = Random.nextGaussianArray1D(10000, mean = 4.0, deviation = 2.0)
                bins = 100
                normalized = true
            }

            line {
                val space = linearSpace(-5.0, 10.0, 100)
                x = space
                y = space.elementWise { normalPdf(it, mean = 4.0, deviation = 2.0) }
            }

        }
    }


}