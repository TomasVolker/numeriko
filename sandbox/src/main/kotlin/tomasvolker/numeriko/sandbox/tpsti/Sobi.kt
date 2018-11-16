package tomasvolker.numeriko.sandbox.tpsti

import tomasvolker.numeriko.core.dsl.D
import tomasvolker.numeriko.core.primitives.squared
import tomasvolker.numeriko.core.primitives.sumDouble
import kotlin.math.sqrt


fun SignalEnsemble.tr(i0: Int, i1: Int): Double =
        estimateAutoCorrelation(i0, i1, 0)

fun SignalEnsemble.off(i0: Int, i1: Int, window: Int): Double {

    val triangleCount = window * (window - 1) / 2.0

    val upperAverage = sumDouble(1 until window) { t ->
        (window - t) * estimateAutoCorrelation(i0, i1, t)
    } / triangleCount

    if (i0 == i1) return upperAverage

    val lowerAverage = sumDouble(1 until window) { t ->
        (window - t) * estimateAutoCorrelation(i1, i0, t)
    } / triangleCount

    return (upperAverage + lowerAverage) / 2.0
}

fun sobi(measurements: SignalEnsemble, window: Int, sigma2: Double = 0.0): SignalEnsemble {

    val f1 = measurements.off(0, 0, window)
    val f2 = measurements.off(1, 1, window)
    val f12 = measurements.off(0, 1, window)

    val t1 = measurements.tr(0, 0) - sigma2
    val t2 = measurements.tr(1, 1) - sigma2
    val t12 = measurements.tr(0, 1)

    val alpha = 2 * f12 * t12 - (f1 * t2 + f2 * t1)
    val beta = 2 * (t12.squared() - t1 * t2)
    val gamma2 = (f1 * t2 - f2 * t1).squared() + 4 * (f12 * t2 - t12 * f2) * (f12 * t1 - t12 * f1)
    val gamma = sqrt(gamma2)
    val d1 = alpha - gamma
    val d2 = alpha + gamma

    val A = D[D[beta * f1  - t1  * d1, beta * f12 - t12 * d2],
              D[beta * f12 - t12 * d1, beta * f2  - t2  * d2]]

    val Ainv = A.inverse()

    return measurements.mixSignals(Ainv)
}