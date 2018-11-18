package tomasvolker.numeriko.sandbox.solving

import tomasvolker.kyplot.dsl.histogram
import tomasvolker.kyplot.dsl.line
import tomasvolker.kyplot.dsl.showPlot
import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.index.Last
import tomasvolker.numeriko.core.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.factory.asDoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.linearalgebra.linearSpace
import tomasvolker.numeriko.core.probability.continuous.ExponentialDistribution
import tomasvolker.numeriko.core.probability.continuous.NormalDistribution
import tomasvolker.numeriko.core.probability.continuous.UniformDistribution
import kotlin.math.absoluteValue

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

    println((matrix outer a2).contract(1, 2))
    println(matrix matMul a2)


    val distribution = NormalDistribution(1.2, 0.6)

    val values = distribution.nextDoubleArray1D(10000)

    showPlot {

        histogram {
            data = values
            bins = 50
            normalized = true
        }

        line {
            val space = linearSpace(values.min() ?: 0.0, values.max() ?: 1.0, 100)
            x = space
            y = space.elementWise { distribution.probabilityDensity(it) }
        }

    }

    val cdfX = values.sorted().toDoubleArray().asDoubleArray1D()
    val cdfY = linearSpace(start = 0.0, stop = 1.0, amount = cdfX.size)

    showPlot {

        line {
            x = cdfX
            y = cdfY
        }

        line {
            val space = linearSpace(cdfX[0], cdfX[Last], 100)
            x = space
            y = space.elementWise { distribution.cumulativeDensity(it) }
        }

    }

}