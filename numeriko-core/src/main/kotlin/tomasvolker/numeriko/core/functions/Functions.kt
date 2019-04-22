package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.core.interfaces.arraynd.double.DoubleArrayND
import tomasvolker.numeriko.core.interfaces.iteration.elementWise
import kotlin.math.*

typealias RealFunction1 = (Double) -> Double

operator fun RealFunction1.invoke(array: DoubleArrayND): DoubleArrayND =
        array.elementWise { this(it) }

fun cos  (array: DoubleArrayND): DoubleArrayND = array.elementWise { cos(it) }
fun sin  (array: DoubleArrayND): DoubleArrayND = array.elementWise { sin(it) }
fun tan  (array: DoubleArrayND): DoubleArrayND = array.elementWise { tan(it) }
fun cosh (array: DoubleArrayND): DoubleArrayND = array.elementWise { cosh(it) }
fun sinh (array: DoubleArrayND): DoubleArrayND = array.elementWise { sinh(it) }
fun tanh (array: DoubleArrayND): DoubleArrayND = array.elementWise { tanh(it) }
fun exp  (array: DoubleArrayND): DoubleArrayND = array.elementWise { exp(it) }
fun ln   (array: DoubleArrayND): DoubleArrayND = array.elementWise { ln(it) }
fun log10(array: DoubleArrayND): DoubleArrayND = array.elementWise { log10(it) }
fun log2 (array: DoubleArrayND): DoubleArrayND = array.elementWise { log2(it) }
