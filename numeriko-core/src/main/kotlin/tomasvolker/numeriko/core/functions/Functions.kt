package tomasvolker.numeriko.core.functions

import tomasvolker.numeriko.lowrank.interfaces.array1d.double.DoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.MutableDoubleArray1D
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.elementWise
import tomasvolker.numeriko.lowrank.interfaces.array1d.double.sumBy
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.DoubleArray2D
import tomasvolker.numeriko.lowrank.interfaces.array2d.double.elementWise

typealias RealFunction1 = (Double) -> Double

operator fun RealFunction1.invoke(array: DoubleArray1D): DoubleArray1D =
        array.elementWise { this(it) }

operator fun RealFunction1.invoke(array: DoubleArray2D): DoubleArray2D =
        array.elementWise { this(it) }

fun cos  (array: DoubleArray1D): DoubleArray1D = array.elementWise { cos(it) }
fun sin  (array: DoubleArray1D): DoubleArray1D = array.elementWise { sin(it) }
fun tan  (array: DoubleArray1D): DoubleArray1D = array.elementWise { tan(it) }
fun cosh (array: DoubleArray1D): DoubleArray1D = array.elementWise { cosh(it) }
fun sinh (array: DoubleArray1D): DoubleArray1D = array.elementWise { sinh(it) }
fun tanh (array: DoubleArray1D): DoubleArray1D = array.elementWise { tanh(it) }
fun exp  (array: DoubleArray1D): DoubleArray1D = array.elementWise { exp(it) }
fun ln   (array: DoubleArray1D): DoubleArray1D = array.elementWise { ln(it) }
fun log10(array: DoubleArray1D): DoubleArray1D = array.elementWise { log10(it) }
fun log2 (array: DoubleArray1D): DoubleArray1D = array.elementWise { log2(it) }

fun cos  (array: DoubleArray2D): DoubleArray2D = array.elementWise { cos(it) }
fun sin  (array: DoubleArray2D): DoubleArray2D = array.elementWise { sin(it) }
fun tan  (array: DoubleArray2D): DoubleArray2D = array.elementWise { tan(it) }
fun cosh (array: DoubleArray2D): DoubleArray2D = array.elementWise { cosh(it) }
fun sinh (array: DoubleArray2D): DoubleArray2D = array.elementWise { sinh(it) }
fun tanh (array: DoubleArray2D): DoubleArray2D = array.elementWise { tanh(it) }
fun exp  (array: DoubleArray2D): DoubleArray2D = array.elementWise { exp(it) }
fun ln   (array: DoubleArray2D): DoubleArray2D = array.elementWise { ln(it) }
fun log10(array: DoubleArray2D): DoubleArray2D = array.elementWise { log10(it) }
fun log2 (array: DoubleArray2D): DoubleArray2D = array.elementWise { log2(it) }
