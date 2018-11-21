package tomasvolker.numeriko.core.probability.discrete

import kotlin.random.Random

class BernulliDistribution(
        val p: Double
): DiscreteProbabilityDistribution {

    override val mean: Double
        get() = p

    override val variance: Double
        get() = p * (1 - p)

    override fun probability(x: Int): Double = when(x) {
        0 -> 1 - p
        1 -> p
        else -> 0.0
    }

    override fun cumulativeDensity(x: Int): Double = when {
        x <= 0.0 -> 0.0
        x <= 1.0 -> 1 - p
        else -> 1.0
    }

    override fun nextInt(random: Random): Int = random.nextInt(1)

    fun nextBoolean(random: Random = Random): Boolean = random.nextDouble() <= p

}