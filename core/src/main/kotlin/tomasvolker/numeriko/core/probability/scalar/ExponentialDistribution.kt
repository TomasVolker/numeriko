package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.primitives.squared
import kotlin.math.exp
import kotlin.math.ln
import kotlin.random.Random

class ExponentialDistribution(
        val lambda: Double = 1.0
): ProbabilityDistribution {

    override val variance: Double
        get() = 1.0 / lambda.squared()

    override val mean: Double
        get() = 1.0 / lambda

    override fun pdf(x: Double): Double =
            if (0.0 < x)
                lambda * exp(-lambda * x)
            else
                0.0

    override fun cdf(x: Double): Double =
            if (0.0 < x)
                1 - exp(-lambda * x)
            else
                0.0

    override fun nextDouble(random: Random): Double = -ln(random.nextDouble()) / lambda

}