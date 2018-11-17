package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.primitives.squared
import kotlin.random.Random

class UniformDistribution(
        val min: Double = 0.0,
        val max: Double = 1.0
): ProbabilityDistribution {

    override val variance: Double
        get() = (max - min).squared() / 12.0

    override val mean: Double
        get() = (min + max) / 2.0

    override fun pdf(x: Double): Double =
            if (x in min..max)
                1.0 / (max - min)
            else
                0.0

    override fun cdf(x: Double): Double =
            if (x in min..max)
                (x - min) / (max - min)
            else
                0.0

    override fun nextDouble(random: Random): Double = random.nextDouble(min, max)

}