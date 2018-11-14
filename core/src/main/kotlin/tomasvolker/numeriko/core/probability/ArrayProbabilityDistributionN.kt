package tomasvolker.numeriko.core.probability

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D
import tomasvolker.numeriko.core.primitives.squared
import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JavaRandom

fun normalPdf(x: Double, mean: Double, std: Double): Double =
        exp(-((x-mean).squared()) / (2 * std.squared())) / (sqrt(2 * PI) * std)

fun normalPdf(
        x: DoubleArray1D,
        mean: DoubleArray1D,
        invCovariance: DoubleArray2D,
        covarianceDeterminant: Double = 1.0 / invCovariance.determinant()
): Double {
    val dimension = x.size
    return exp(-(invCovariance.quadraticForm(x-mean))/2.0) / (2 * PI * covarianceDeterminant).pow(dimension / 2.0)
}

interface DoubleProbabilityDistribution {

    val mean: Double
    val variance: Double

    fun pdf(x: Double): Double
    fun cdf(x: Double): Double

    fun nextSample(): Double

    fun generateSamples(size: Int): DoubleArray1D =
            doubleArray1D(size) { i -> nextSample() }

    fun sequence(): Sequence<Double> =
            generateSequence { nextSample() }

}

interface ArrayProbabilityDistributionN {
    fun pdf(x: DoubleArray1D): Double
}

class UniformDistribution(
        val min: Double = 0.0,
        val max: Double = 1.0
): DoubleProbabilityDistribution {

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

    override fun nextSample(): Double = Random.nextDouble(min, max)

}

class ExponentialDistribution(
        val lambda: Double = 1.0
): DoubleProbabilityDistribution {

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

    override fun nextSample(): Double = -ln(Random.nextDouble()) / lambda

}


class DoubleNormalDistribution(
        override val mean: Double = 0.0,
        val deviation: Double = 1.0
): DoubleProbabilityDistribution {

    override val variance: Double = deviation.squared()

    val normalization: Double = 1.0 / (sqrt(2 * PI) * deviation)

    override fun pdf(x: Double): Double =
            normalization * exp(-(x-mean).squared() / (2 * variance))

    override fun cdf(x: Double): Double = TODO()

    override fun nextSample(): Double =
            JavaRandom().nextGaussian() * deviation + mean

}

class ArrayNormalDistribution(
        val mean: DoubleArray1D,
        val covariance: DoubleArray2D
): ArrayProbabilityDistributionN {

    init {
        require(covariance.isSquare())
        require(mean.size == covariance.shape0)
    }

    val dimension: Int = mean.size

    val inverseCovariance: DoubleArray2D = covariance.inverse()
    val covarianceDeterminant: Double = covariance.determinant()

    val normalization: Double = 1.0 / (2 * PI * covarianceDeterminant).pow(dimension / 2.0)

    override fun pdf(x: DoubleArray1D): Double =
            normalization * exp(-(inverseCovariance.quadraticForm(x-mean))/2.0)

}
