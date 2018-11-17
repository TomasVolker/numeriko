package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.primitives.squared
import kotlin.math.*
import java.util.Random as JavaRandom
import kotlin.random.Random

class NormalDistribution(
        override val mean: Double = 0.0,
        val deviation: Double = 1.0
): ProbabilityDistribution {

    private var availableValue: Boolean = false
    private var value: Double = 0.0

    override val variance: Double = deviation.squared()

    val normalization: Double = 1.0 / (sqrt(2 * PI) * deviation)

    override fun pdf(x: Double): Double =
            normalization * exp(-(x - mean).squared() / (2 * variance))

    override fun cdf(x: Double): Double = TODO()

    override fun nextDouble(random: Random): Double {

        if (availableValue) {
            availableValue = false
            return value
        } else {
            // Box muller
            val angle = random.nextDouble(2 * PI)
            val abs = sqrt(-2 * ln(random.nextDouble()))

            value = abs * sin(angle) * deviation + mean

            return abs * cos(angle) * deviation + mean
        }

    }

}

fun normalPdf(x: Double, mean: Double, std: Double): Double =
        exp(-((x-mean).squared()) / (2 * std.squared())) / (sqrt(2 * PI) * std)

