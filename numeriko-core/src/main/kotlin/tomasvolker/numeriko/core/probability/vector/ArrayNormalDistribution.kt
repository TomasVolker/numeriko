package tomasvolker.numeriko.core.probability.vector

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.functions.minus
import tomasvolker.numeriko.core.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.core.functions.determinant
import tomasvolker.numeriko.core.functions.inverse
import tomasvolker.numeriko.core.functions.quadraticForm
import tomasvolker.numeriko.core.interfaces.array2d.generic.isSquare
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow

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
            normalization * exp(-(inverseCovariance.quadraticForm(x - mean)) / 2.0)

}

fun normalPdf(
        x: DoubleArray1D,
        mean: DoubleArray1D,
        invCovariance: DoubleArray2D,
        covarianceDeterminant: Double = 1.0 / invCovariance.determinant()
): Double {
    val dimension = x.size
    return exp(-(invCovariance.quadraticForm(x-mean))/2.0) / (2 * PI * covarianceDeterminant).pow(dimension / 2.0)
}

