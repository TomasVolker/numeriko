package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import kotlin.random.Random

interface ProbabilityDistribution {

    val mean: Double
    val variance: Double

    fun pdf(x: Double): Double
    fun cdf(x: Double): Double

    fun nextDouble(random: Random = Random): Double

    fun nextDoubleArray1D(size: Int, random: Random = Random): DoubleArray1D =
            doubleArray1D(size) { nextDouble(Random) }

    fun sequence(random: Random = Random): Sequence<Double> =
            generateSequence { nextDouble(Random) }

}