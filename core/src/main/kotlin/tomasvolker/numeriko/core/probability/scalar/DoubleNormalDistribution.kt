package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.primitives.squared
import java.util.Random as JavaRandom
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt
import kotlin.random.Random

class DoubleNormalDistribution(
        override val mean: Double = 0.0,
        val deviation: Double = 1.0
): DoubleProbabilityDistribution {

    override val variance: Double = deviation.squared()

    val normalization: Double = 1.0 / (sqrt(2 * PI) * deviation)

    override fun pdf(x: Double): Double =
            normalization * exp(-(x - mean).squared() / (2 * variance))

    override fun cdf(x: Double): Double = TODO()

    override fun nextSample(): Double =
            JavaRandom().nextGaussian() * deviation + mean

}

fun normalPdf(x: Double, mean: Double, std: Double): Double =
        exp(-((x-mean).squared()) / (2 * std.squared())) / (sqrt(2 * PI) * std)

