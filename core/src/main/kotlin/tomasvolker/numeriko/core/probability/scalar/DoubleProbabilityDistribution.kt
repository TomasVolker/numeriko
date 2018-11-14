package tomasvolker.numeriko.core.probability.scalar

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.factory.doubleArray1D

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