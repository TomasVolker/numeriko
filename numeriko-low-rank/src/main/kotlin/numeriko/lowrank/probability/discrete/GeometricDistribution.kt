package numeriko.lowrank.probability.discrete

import kotlin.math.pow
import kotlin.random.Random

class GeometricDistribution(
        val p: Double
): DiscreteProbabilityDistribution {

    override val mean: Double
        get() = 1.0 / p

    override val variance: Double
        get() = (1 - p) / (p * p)

    override fun probability(x: Int): Double = when {
        0 < x -> (1 - p).pow(x) * p
        else -> 0.0
    }

    override fun cumulativeDensity(x: Int): Double = when {
        0 < x -> 1 - (1 - p).pow(x)
        else -> 0.0
    }

    override fun nextInt(random: Random): Int = TODO()

}

