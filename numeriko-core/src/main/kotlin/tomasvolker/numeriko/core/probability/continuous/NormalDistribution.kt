package tomasvolker.numeriko.core.probability.continuous

import tomasvolker.numeriko.core.primitives.squared
import kotlin.math.*
import java.util.Random as JavaRandom
import kotlin.random.Random

class NormalDistribution(
        override val mean: Double = 0.0,
        val deviation: Double = 1.0
): ContinuousProbabilityDistribution {

    private var availableValue: Boolean = false
    private var value: Double = 0.0

    override val variance: Double = deviation.squared()

    val normalization: Double = 1.0 / (sqrt(2 * PI) * deviation)

    override fun probabilityDensity(x: Double): Double =
            normalization * exp(-(x - mean).squared() / (2 * variance))

    override fun cumulativeDensity(x: Double): Double =
            0.5 * (1 + erf((x - mean) / (deviation * sqrt(2.0))))

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

/**
 * Abramowitz and Stegun
 */
fun erf(x: Double): Double {

    if (x < 0.0)
        return -erf(-x)

    val p = 0.3275911
    val a1 = 0.254829592
    val a2 = -0.284496736
    val a3 = 1.421413741
    val a4 = -1.453152027
    val a5 = 1.061405429

    val t = 1 / (1 + p * x)
    val t2 = t * t
    val t3 = t2 * t
    val t4 = t3 * t
    val t5 = t4 * t

    return 1 - (a1 * t + a2 * t2 + a3 * t3 + a4 * t4 + a5 * t5) * exp(-x * x)
}