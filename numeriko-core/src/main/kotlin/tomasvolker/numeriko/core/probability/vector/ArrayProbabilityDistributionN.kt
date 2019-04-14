package tomasvolker.numeriko.core.probability.vector

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D

interface ArrayProbabilityDistributionN {
    fun pdf(x: DoubleArray1D): Double
}

