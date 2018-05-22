package tomasvolker.numeriko.core.linearalgebra

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.elementWise
import kotlin.math.*

typealias RealFunction1 = (Double) -> Double

inline operator fun RealFunction1.invoke(array: DoubleArray1D): DoubleArray1D =
        array.elementWise { this(it) }

fun cos(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { cos(it) }

fun sin(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { sin(it) }

fun tan(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { tan(it) }

fun cosh(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { cosh(it) }

fun sinh(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { sinh(it) }

fun tanh(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { tanh(it) }

fun exp(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { exp(it) }

fun ln(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { ln(it) }

fun log10(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { log10(it) }

fun log2(array: DoubleArray1D): MutableDoubleArray1D =
        array.elementWise { log2(it) }