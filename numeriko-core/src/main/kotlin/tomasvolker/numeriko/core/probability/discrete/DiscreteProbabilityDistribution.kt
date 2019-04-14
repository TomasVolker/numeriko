package tomasvolker.numeriko.core.probability.discrete

import tomasvolker.numeriko.lowrank.interfaces.array1d.integer.IntArray1D
import tomasvolker.numeriko.core.interfaces.factory.intArray1D
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random

interface DiscreteProbabilityDistribution {

    val mean: Double
    val variance: Double

    fun probability(x: Int): Double

    fun cumulativeDensity(x: Int): Double
    fun cumulativeDensity(x: Double): Double =
            cumulativeDensity(floor(x).roundToInt())

    fun nextInt(random: Random = Random): Int

    fun nextIntArray1D(size: Int, random: Random = Random): IntArray1D =
            intArray1D(size) { nextInt(Random) }

    fun sequence(random: Random = Random): Sequence<Int> =
            generateSequence { nextInt(Random) }

}

fun DiscreteProbabilityDistribution.pmf(x: Int): Double = probability(x)
fun DiscreteProbabilityDistribution.cdf(x: Double): Double = cumulativeDensity(x)
