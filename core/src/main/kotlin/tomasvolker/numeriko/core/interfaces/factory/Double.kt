package tomasvolker.numeriko.core.interfaces.factory

import tomasvolker.numeriko.core.interfaces.double.array1d.DoubleArray1D
import tomasvolker.numeriko.core.interfaces.double.array1d.MutableDoubleArray1D

fun DoubleArray.asDoubleArray1D(): DoubleArray1D = doubleArray1D(this)

fun DoubleArray.asMutableDoubleArray1D(): DoubleArray1D = mutableDoubleArray1D(this)

fun doubleArray1D(data: DoubleArray): DoubleArray1D = mutableDoubleArray1D(data)

inline fun doubleArray1D(size: Int, init: (index: Int)->Double): DoubleArray1D =
        mutableDoubleArray1D(size, init)

fun mutableDoubleArray1D(data: DoubleArray): MutableDoubleArray1D =
        defaultFactory.mutableDoubleArray1D(data)

inline fun mutableDoubleArray1D(size: Int, init: (index: Int)->Double): MutableDoubleArray1D =
        mutableDoubleArray1D(DoubleArray(size) {i -> init(i) })

fun doubleArray1DOf(vararg values: Double) = doubleArray1D(values)

fun mutableDoubleArray1DOf(vararg values: Double) =
        mutableDoubleArray1D(values)

fun copy(array: DoubleArray1D): DoubleArray1D =
        defaultFactory.copy(array)

fun mutableCopy(array: DoubleArray1D): MutableDoubleArray1D =
        defaultFactory.mutableCopy(array)

fun doubleZeros(size: Int): DoubleArray1D =
        defaultFactory.doubleZeros(size)

fun mutableDoubleZeros(size: Int): MutableDoubleArray1D =
        defaultFactory.mutableDoubleZeros(size)