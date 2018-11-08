package tomasvolker.numeriko.core.probability

import tomasvolker.numeriko.core.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.core.primitives.squared
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

fun normalPdf(x: Double, mean: Double, std: Double): Double =
        exp(-((x-mean).squared()) / (2 * std.squared())) / (sqrt(2 * PI) * std)

