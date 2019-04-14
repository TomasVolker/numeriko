package tomasvolker.numeriko.core.probability.continuous

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import kotlin.random.Random

interface ContinuousProbabilityDistribution {

    val mean: Double
    val variance: Double

    fun probabilityDensity(x: Double): Double
    fun cumulativeDensity(x: Double): Double

    fun nextDouble(random: Random = Random): Double

    fun nextDoubleArray1D(size: Int, random: Random = Random): DoubleArray1D =
            doubleArray1D(size) { nextDouble(Random) }

    fun sequence(random: Random = Random): Sequence<Double> =
            generateSequence { nextDouble(Random) }

}

fun ContinuousProbabilityDistribution.pdf(x: Double): Double = probabilityDensity(x)
fun ContinuousProbabilityDistribution.cdf(x: Double): Double = cumulativeDensity(x)
