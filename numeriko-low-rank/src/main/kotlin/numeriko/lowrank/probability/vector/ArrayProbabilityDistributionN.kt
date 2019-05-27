package numeriko.lowrank.probability.vector

import numeriko.lowrank.interfaces.array1d.double.DoubleArray1D

interface ArrayProbabilityDistributionN {
    fun pdf(x: DoubleArray1D): Double
}
